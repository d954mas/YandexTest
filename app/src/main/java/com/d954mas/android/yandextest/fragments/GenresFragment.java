package com.d954mas.android.yandextest.fragments;


import android.content.Intent;
import android.support.v4.app.Fragment;

import com.d954mas.android.yandextest.activities.ArtistGenreListActivity;
import com.d954mas.android.yandextest.adapters.ArrayAdapter;
import com.d954mas.android.yandextest.adapters.GenreArrayAdapter;
import com.d954mas.android.yandextest.utils.DataSingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenresFragment extends ArrayFragment<String> {

    public GenresFragment() {}

    @Override
    protected String getSearchName(int position) {
        return dataList.get(position);
    }

    @Override
    protected ArrayAdapter getArrayAdapter() {
        return new GenreArrayAdapter(DataSingleton.get().getGenres());
    }

    @Override
    protected void itemClicked(int position) {
        Intent intent=new Intent(getContext(),ArtistGenreListActivity.class);
        intent.putExtra("genre", filteredDataList.get(position));
        startActivity(intent);
    }


}
