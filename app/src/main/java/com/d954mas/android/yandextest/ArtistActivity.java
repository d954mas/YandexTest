package com.d954mas.android.yandextest;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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
                ImageLoader.getInstance().displayImage(artistBean.bigImageUrl,imageView);
                ((TextView) findViewById(R.id.artist_biography)).setText(artistBean.description);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
