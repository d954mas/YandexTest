package com.d954mas.android.yandextest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.d954mas.android.yandextest.FragmentInjectors;
import com.d954mas.android.yandextest.activities.ArtistActivity;
import com.d954mas.android.yandextest.adapters.ArrayAdapter;
import com.d954mas.android.yandextest.adapters.ArtistArrayAdapter;
import com.d954mas.android.yandextest.models.ArtistModel;
import com.d954mas.android.yandextest.utils.DataSingleton;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistsFragment extends ArrayFragment<ArtistModel> {

    @Inject
    DataSingleton dataSingleton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        FragmentInjectors.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getSearchName(int position) {
        return dataList.get(position).name;
    }

    @Override
    protected ArrayAdapter getArrayAdapter() {
        return new ArtistArrayAdapter(dataSingleton.getArtists());
    }

    @Override
    protected void itemClicked(int position) {
        ArtistModel artistModel = filteredDataList.get(position);
        Intent intent=new Intent(getContext(),ArtistActivity.class);
        intent.putExtra("artist", artistModel.getJson().toString());
        startActivity(intent);
    }
}
