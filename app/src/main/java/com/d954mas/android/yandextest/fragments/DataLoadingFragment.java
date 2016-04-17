package com.d954mas.android.yandextest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.d954mas.android.yandextest.models.DataLoadingModel;


public class DataLoadingFragment extends Fragment {
    private final DataLoadingModel dataLoadingModel;

    public DataLoadingFragment() {
        dataLoadingModel = new DataLoadingModel();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public DataLoadingModel getDataLoadingModel() {
        return dataLoadingModel;
    }
}
