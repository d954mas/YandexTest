package com.d954mas.android.yandextest.utils;

import android.content.Context;
import android.util.Log;

import com.d954mas.android.yandextest.models.ArtistModel;

import org.json.JSONArray;
import org.json.JSONException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

//Singleton для получения данных об артистах из любого места приложения
@Singleton
public class DataSingleton {
    private static final String TAG="DataSingleton";
    private static final String ARTIST_JSON_KEY = "cachedArtists";
    private Context context;
    private List<ArtistModel> artists;
    private HashMap<String,List<ArtistModel>> artistsByGenre;

    @Inject
    DataSingleton(Context context){
        this.context=context;
        String jsonString=CacheHelper.readCacheString(context, ARTIST_JSON_KEY);
        if(jsonString!=null){
            artists=parseData(jsonString);
            Log.i(TAG,"get data from cache");
        }
    }

    private List<ArtistModel> parseData(String json){
        CacheHelper.cacheString(context, ARTIST_JSON_KEY,json);
        Log.i(TAG, "save artists to cache");

        try {
            List<ArtistModel> artist = new ArrayList<>();
            JSONArray artistList = new JSONArray(json);
            artistsByGenre=new HashMap<>();
            for (int i = 0; i < artistList.length(); i++) {
                ArtistModel artistModel=new ArtistModel(artistList.getJSONObject(i));
                artist.add(artistModel);
                for(String genre:artistModel.genres){
                   List<ArtistModel> artistGenreList;
                    if(artistsByGenre.containsKey(genre)){
                        artistGenreList=artistsByGenre.get(genre);
                    }else{
                        artistGenreList=new ArrayList<>();
                        artistsByGenre.put(genre,artistGenreList);
                    }
                    artistGenreList.add(artistModel);
                }
            }
            Collections.sort(artist, (lhs, rhs) -> lhs.name.compareTo(rhs.name));
            return artist;

        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("bad json data");
        }
    }

    public void setData(String json){
        artists=parseData(json);
    }

    public boolean hasData(){
        return artists!=null;
    }

    public List<ArtistModel> getArtists() {
        return artists;
    }

    public List<String> getGenres() {
        return new ArrayList<>(artistsByGenre.keySet());
    }

    public List<ArtistModel> getArtistsByGenre(String genre){
        return artistsByGenre.get(genre);
    }
}
