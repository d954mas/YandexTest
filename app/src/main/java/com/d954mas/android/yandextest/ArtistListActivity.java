package com.d954mas.android.yandextest;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArtistListActivity extends AppCompatActivity {
    private static final String LIST_STATE = "listState";
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
                if (savedInstanceState != null) {
                    lvMain.onRestoreInstanceState(savedInstanceState.getParcelable(LIST_STATE));
                }
            }
        }.execute();
    }


    protected List<ArtistBean> readArtistJson() throws JSONException {
        String jsonString = loadJSONStringFromResources();
        JSONArray artistList = null;
        artistList = new JSONArray(jsonString);
        List<ArtistBean> artist = new ArrayList<>();
        for (int i = 0; i < artistList.length(); i++) {
            artist.add(new ArtistBean(artistList.getJSONObject(i)));
        }
        Collections.sort(artist, new Comparator<ArtistBean>() {
            @Override
            public int compare(ArtistBean lhs, ArtistBean rhs) {
                return lhs.name.compareTo(rhs.name);
            }
        });
        return artist;
    }

    //todo сделать более лаконичное чтение
    //loading json file with artist
    protected String loadJSONStringFromResources() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.artists);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
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

