package com.d954mas.android.yandextest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.models.ArtistModel;
import com.d954mas.android.yandextest.utils.TextUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

//активити для отображения всей информации об одном артисте
public class ArtistActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        if(intent!=null){
            try {
                ArtistModel artistModel =new ArtistModel(new JSONObject(intent.getStringExtra("artist")));
                ImageView imageView= ((ImageView) findViewById(R.id.artist_big_image));
                imageView.setImageBitmap(null);
                setTitle(artistModel.name);
                ImageLoader.getInstance().displayImage(artistModel.bigImageUrl, imageView);
                ((TextView) findViewById(R.id.artist_biography)).setText(artistModel.description);
                StringBuilder builder=new StringBuilder();
                for(String genre: artistModel.genres){
                    builder.append(genre);
                    builder.append(", ");
                }
                builder.delete(builder.length() - 2, builder.length());
                ((TextView) findViewById(R.id.artist_genres)).setText(builder.toString());
                ((TextView) findViewById(R.id.artist_songs)).setText(artistModel.tracks + " " + TextUtils.getStringByNumber(artistModel.tracks, "песня", "песни", "песен"));
                ((TextView) findViewById(R.id.artist_albums)).setText(artistModel.albums+" "+TextUtils.getStringByNumber(artistModel.tracks, "альбом", "альбома", "альбомов"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
