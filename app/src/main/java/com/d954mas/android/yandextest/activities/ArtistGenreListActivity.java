package com.d954mas.android.yandextest.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.d954mas.android.yandextest.ActivityInjectors;
import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.fragments.ArtistsFragment;
import com.d954mas.android.yandextest.models.ArtistModel;
import com.d954mas.android.yandextest.utils.DataSingleton;

import javax.inject.Inject;
import java.util.List;

//активити для отображения всех артистов одного жанра
public class ArtistGenreListActivity extends AppCompatActivity{

    @Inject
    DataSingleton dataSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInjectors.inject(this);
        Intent intent=getIntent();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_artist_genre_list);
        if(intent!=null){
            String genre=intent.getStringExtra("genre");
            setTitle(genre);
            List<ArtistModel> artists= dataSingleton.getArtistsByGenre(genre);
            ArtistsFragment artistsFragment= (ArtistsFragment) getSupportFragmentManager().findFragmentById(R.id.artists_fragment);
            artistsFragment.setData(artists);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
               finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
            }
}
