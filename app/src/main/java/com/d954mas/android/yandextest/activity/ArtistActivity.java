package com.d954mas.android.yandextest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.d954mas.android.yandextest.ArtistBean;
import com.d954mas.android.yandextest.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

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
                ArtistBean artistBean=new ArtistBean(new JSONObject(intent.getStringExtra("artist")));
                ImageView imageView= ((ImageView) findViewById(R.id.artist_big_image));
                imageView.setImageBitmap(null);
                setTitle(artistBean.name);
                ImageLoader.getInstance().displayImage(artistBean.bigImageUrl, imageView);
                ((TextView) findViewById(R.id.artist_biography)).setText(artistBean.description);
                StringBuilder builder=new StringBuilder();
                for(String genre:artistBean.genres){
                    builder.append(genre);
                    builder.append(", ");
                }
                builder.delete(builder.length() - 2, builder.length());
                ((TextView) findViewById(R.id.artist_genres)).setText(builder.toString());
                ((TextView) findViewById(R.id.artist_songs)).setText(artistBean.tracks+" "+getStringForSongs(artistBean.tracks));
                ((TextView) findViewById(R.id.artist_albums)).setText(artistBean.albums+" "+getStringForAlbum(artistBean.albums));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected String getStringForSongs(int songs){
        songs=songs%10;
        if(songs==1)return "песня";
        else if(songs<5)return "песни";
        else return "песен";
    }
    protected String getStringForAlbum(int songs){
        songs=songs%10;
        if(songs==1)return "альбом";
        else if(songs<5)return "альбома";
        else return "альбомов";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, ArtistListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



    }
}
