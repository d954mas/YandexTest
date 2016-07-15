package com.d954mas.android.yandextest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.d954mas.android.yandextest.FragmentInjectors;
import com.d954mas.android.yandextest.activities.ArtistGenreListActivity;
import com.d954mas.android.yandextest.adapters.ArrayAdapter;
import com.d954mas.android.yandextest.adapters.GenreArrayAdapter;
import com.d954mas.android.yandextest.utils.DataSingleton;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenresFragment extends ArrayFragment<String> {

    @Inject
    DataSingleton dataSingleton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        FragmentInjectors.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getSearchName(int position) {
        return dataList.get(position);
    }

    @Override
    protected ArrayAdapter getArrayAdapter() {
        return new GenreArrayAdapter(dataSingleton.getGenres());
    }

    @Override
    protected void itemClicked(int position) {
        Intent intent=new Intent(getContext(),ArtistGenreListActivity.class);
        intent.putExtra("genre", filteredDataList.get(position));
        startActivity(intent);
    }


}
