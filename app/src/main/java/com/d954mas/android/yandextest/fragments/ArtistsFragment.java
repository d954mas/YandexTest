package com.d954mas.android.yandextest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.adapters.ArrayAdapter;
import com.d954mas.android.yandextest.adapters.ArtistArrayAdapter;
import com.d954mas.android.yandextest.models.ArtistModel;
import com.d954mas.android.yandextest.utils.DataSingleton;

public class ArtistsFragment extends ArrayFragment<ArtistModel> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init() {
        setData(DataSingleton.get().getArtists());
    }

    @Override
    protected String getSearchName(int position) {
        return dataList.get(position).name;
    }

    @Override
    protected ArrayAdapter getArrayAdapter() {
        return new ArtistArrayAdapter(DataSingleton.get().getArtists());
    }

    @Override
    protected void itemClicked(int position) {
        ArtistModel artistModel = filteredDataList.get(position);
        ArtistFragment artistFragment=new ArtistFragment();
        artistFragment.setData(artistModel);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,artistFragment)
                .addToBackStack(null).commit();
    }
}
