package com.d954mas.android.yandextest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtistListActivity extends AppCompatActivity {
    private static final String LIST_STATE = "listState";
    private static final String ARTIST_JSON_KEY = "cachedArtists";
    private Parcelable mListState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);



        //в данном примере это возможно избыточно,тк данные грузятся быстро,
        //и пользователь не успевает увидить полосу загрузки,но если вдруг данных станет много,то это пригодится
        new AsyncTask<Void, Void, Void>() {
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
                progressDialog.dismiss();
                ListView lvMain = (ListView) findViewById(R.id.artist_list);
                lvMain.setTextFilterEnabled(true);


              /*  lvMain.setRecyclerListener(view -> {
                    ImageView imageView= ((ImageView) view.findViewById(R.id.artist_element_image));
                    Drawable drawable = imageView.getDrawable();
                   // if (drawable instanceof BitmapDrawable) {// это всегда правда
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        if(bitmap!=null && !bitmap.isRecycled())bitmap.recycle();
                });*/
                ArtistArrayAdapter adapter = new ArtistArrayAdapter(ArtistListActivity.this, artists);
                // присваиваем адаптер списку
                lvMain.setAdapter(adapter);
                lvMain.setOnItemClickListener((parent, view, position, id) -> {
                    ArtistBean artistBean= (ArtistBean) adapter.getItem(position);
                    Intent intent=new Intent(ArtistListActivity.this,ArtistActivity.class);
                    startActivity(intent);
                });
                if (savedInstanceState != null) {
                    lvMain.onRestoreInstanceState(savedInstanceState.getParcelable(LIST_STATE));
                }
            }
        }.execute();
    }


    protected List<ArtistBean> readArtistJson() throws JSONException {
        String jsonString = getArtisJsonString();
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
            }
        }else{
            return cachedJson;
        }
        throw new RuntimeException("something wrong");
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
        mListState = state.getParcelable(LIST_STATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListState != null)
            ((ListView)findViewById(R.id.artist_list)).onRestoreInstanceState(mListState);
        mListState = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        mListState = ((ListView)findViewById(R.id.artist_list)).onSaveInstanceState();
        state.putParcelable(LIST_STATE, mListState);
    }
}

