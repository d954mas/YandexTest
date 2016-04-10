package com.d954mas.android.yandextest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

public class ArtistListActivity extends AppCompatActivity  {
    private static final String ARTIST_JSON_KEY = "cachedArtists";
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    LoadAsyncTask loadAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        loadAsyncTask=new LoadAsyncTask();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //todo возможно не стоит вызывать эту таску при каждом пересоздании?
        loadAsyncTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update:
                if (loadAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {

                } else {
                    loadAsyncTask = new LoadAsyncTask();
                    loadAsyncTask.execute();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected List<ArtistBean> readArtistJson() throws JSONException {
        String jsonString = getArtisJsonString();
        if(jsonString==null)return new ArrayList<>();
        JSONArray artistList = null;
        artistList = new JSONArray(jsonString);
        List<ArtistBean> artist = new ArrayList<>();
        for (int i = 0; i < artistList.length(); i++) {
            artist.add(new ArtistBean(artistList.getJSONObject(i)));
        }
        Collections.sort(artist, (lhs, rhs) -> lhs.name.compareTo(rhs.name));
        return artist;
    }

    //todo сделать более лаконичное чтение
    //loading json file with artist
    protected String getArtisJsonString() {
        String cachedJson=CacheHelper.readCacheString(ArtistListActivity.this, ARTIST_JSON_KEY);
        if(cachedJson==null){
            try {
                //todo Добавить обработку ошибок при отсутствие интернета
                cachedJson= loadAtristsFromWeb();
                CacheHelper.cacheString(ArtistListActivity.this,ARTIST_JSON_KEY,cachedJson);
                return cachedJson;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return cachedJson;
        }
       // throw new RuntimeException("something wrong");
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
    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
    }

    //загружаем данные с сервера либо с кэша
    private  class LoadAsyncTask extends AsyncTask<Void, Void, Void> {
        protected ProgressDialog progressDialog;
        protected List<ArtistBean> artists;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ArtistListActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                artists = readArtistJson();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Освобождаем ресурсы(Bitmap)
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewPagerAdapter.getArtistsFragment().setData(artists);
            progressDialog.dismiss();
        }
    }
}

