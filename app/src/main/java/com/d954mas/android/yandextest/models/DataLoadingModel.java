package com.d954mas.android.yandextest.models;

import android.database.Observable;
import android.os.AsyncTask;
import android.util.Log;

import com.d954mas.android.yandextest.utils.DataSingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class DataLoadingModel {
    //модель для асинхронной загрузки данных.

    private static final String TAG = "DataLoadingModel";

    private final DataLoadingObservable mObservable = new DataLoadingObservable();
    private LoadAsyncTask loadingTask;
    private boolean isWorking;

    public DataLoadingModel() {
    }

    public void loadData() {
        if (isWorking) {
            return;
        }
        mObservable.notifyStarted();
        isWorking = true;
        loadingTask = new LoadAsyncTask();
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

        protected String loadArtistsFromWeb() throws IOException {
            URL url = new URL("http://download.cdn.yandex.net/mobilization-2016/artists.json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        }

        protected boolean readArtistJson() {
            if (DataSingleton.get().hasData()) {
                Log.i(TAG, "already loaded");
                return true;
            } else {
                try {
                    String jsonString = loadArtistsFromWeb();
                    DataSingleton.get().setData(jsonString);
                    return true;
                } catch (IOException e) {
                    Log.i(TAG, "failed to load data");
                    e.printStackTrace();
                    return false;
                }
            }

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return readArtistJson();
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
