package com.d954mas.android.yandextest.models;

import android.content.Context;
import android.database.Observable;
import android.os.AsyncTask;
import android.util.Log;

import com.d954mas.android.yandextest.utils.CacheHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by user on 11.04.2016.
 */
public class DataLoadingModel {
    private static final String TAG = "DataLoadingModel";
    private static final String ARTIST_JSON_KEY = "cachedArtists";

    private final DataLoadingObservable mObservable = new DataLoadingObservable();
    private LoadAsyncTask loadingTask;
    private boolean isWorking;
    private List<ArtistModel> artists;

    public DataLoadingModel() {
    }

    public void loadData(Context context) {
        if (isWorking) {
            return;
        }
        mObservable.notifyStarted();
        isWorking = true;
        loadingTask = new LoadAsyncTask(context);
        loadingTask.execute();
    }

    public void stopLoadData() {
        if (isWorking) {
            loadingTask.cancel(true);
            isWorking = false;
        }
    }

    public void registerObserver(final Observer observer) {
        mObservable.registerObserver(observer);
        if (isWorking) {
            observer.onSignInStarted(this);
        }
    }

    public void unregisterObserver(final Observer observer) {
        mObservable.unregisterObserver(observer);
    }

    public List<ArtistModel> getArtists() {
        return artists;
    }

    public interface Observer {
        void onSignInStarted(DataLoadingModel signInModel);

        void onSignInSucceeded(DataLoadingModel signInModel);

        void onSignInFailed(DataLoadingModel signInModel);
    }

    private class DataLoadingObservable extends Observable<Observer> {
        public void notifyStarted() {
            for (final Observer observer : mObservers) {
                observer.onSignInStarted(DataLoadingModel.this);
            }
        }

        public void notifySucceeded() {
            for (final Observer observer : mObservers) {
                observer.onSignInSucceeded(DataLoadingModel.this);
            }
        }

        public void notifyFailed() {
            for (final Observer observer : mObservers) {
                observer.onSignInFailed(DataLoadingModel.this);
            }
        }
    }
    private class LoadAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        public LoadAsyncTask(Context context){
            this.context = context;
        }


        protected String loadAtristsFromWeb() throws IOException {
            URL url = new URL("http://download.cdn.yandex.net/mobilization-2016/artists.json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            String json = buffer.toString();
            return json;
        }

        protected List<ArtistModel> readArtistJson(){
            if(artists!=null){
                Log.i(TAG,"already loaded");
                return artists;
            }
            String jsonString=CacheHelper.readCacheString(context, ARTIST_JSON_KEY);
            if(jsonString==null){
                try {
                    jsonString = loadAtristsFromWeb();
                    CacheHelper.cacheString(context, ARTIST_JSON_KEY,jsonString);
                    Log.i(TAG,"get data from internet");
                    if(jsonString==null)return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }else{
                Log.i(TAG,"get data from cache");
            }

            JSONArray artistList = null;
            try {
                List<ArtistModel> artist = new ArrayList<>();
                artistList = new JSONArray(jsonString);
                for (int i = 0; i < artistList.length(); i++) {
                    artist.add(new ArtistModel(artistList.getJSONObject(i)));
                }
                Collections.sort(artist, (lhs, rhs) -> lhs.name.compareTo(rhs.name));
                return artist;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            artists = readArtistJson();
            if(artists!=null)return true;
            return false;
        }

        //Освобождаем ресурсы(Bitmap)
        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            isWorking = false;
            if (success) {
                Log.i(TAG,"successfully get data");
                mObservable.notifySucceeded();
            } else {
                Log.i(TAG,"error while get data");
                mObservable.notifyFailed();
            }
        }
    }
}
