package com.d954mas.android.yandextest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by user on 09.04.2016.
 */
public class ArtistBean {
    private String url;
    protected JSONObject artist;

    public long id;
    public String name;
    public String smallImageUrl;
    public String bigImageUrl;
    public ArrayList<String> genres;
    public int tracks;
    public int albums;
    public String link;
    public String description;

    public ArtistBean(){
        genres=new ArrayList<>();
    }

    public ArtistBean(JSONObject artist){
        this();
        changeData(artist);
    }

    public void changeData(JSONObject artist){
        this.artist = artist;
        genres.clear();
        try {
            id=artist.getLong("id");
            name=artist.getString("name");
            JSONArray genresJsonArray=artist.getJSONArray("genres");
            for (int i = 0; i < genresJsonArray.length(); i++) {
                genres.add(genresJsonArray.getString(i));
            }
            smallImageUrl=artist.getJSONObject("cover").getString("small");
            bigImageUrl=artist.getJSONObject("cover").getString("big");
            tracks=artist.getInt("tracks");
            albums=artist.getInt("albums");
            if(artist.has("link")){
                link=artist.getString("link");
            }else{
                link="";
            }

            description=artist.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
