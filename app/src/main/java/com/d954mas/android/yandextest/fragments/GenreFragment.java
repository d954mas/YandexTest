package com.d954mas.android.yandextest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d954mas.android.yandextest.utils.DataSingleton;

/**
 * Created by user on 7/15/16.
 */

public class GenreFragment extends ArtistsFragment {
    private static final String GENRE="GENRE";
    private String genre;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            genre=savedInstanceState.getString(GENRE);
            setGenre(genre);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(genre);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void init() {
    }

    public void setGenre(String genre){
        this.genre = genre;
        setData(DataSingleton.get().getArtistsByGenre(genre));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GENRE,genre);
    }
}
