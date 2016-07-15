package com.d954mas.android.yandextest.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.adapters.ArrayAdapter;
import com.d954mas.android.yandextest.adapters.GenreArrayAdapter;
import com.d954mas.android.yandextest.utils.DataSingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenresFragment extends ArrayFragment<String> {

    public GenresFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData(DataSingleton.get().getGenres());
    }

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
        //Intent intent=new Intent(getContext(),ArtistGenreListActivity.class);
        //intent.putExtra("genre", filteredDataList.get(position));
        //startActivity(intent);
        String genre=filteredDataList.get(position);
        GenreFragment genreFragment=new GenreFragment();
        genreFragment.setGenre(genre);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,genreFragment)
                .addToBackStack(null).commit();
    }


}
