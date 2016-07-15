package com.d954mas.android.yandextest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.d954mas.android.yandextest.FragmentInjectors;
import com.d954mas.android.yandextest.models.DataLoadingModel;

import javax.inject.Inject;

//retain fragment для загрузки данные,не имеет ui,нужен только
//для сохранения и доступа к DataLoadingModel
public class DataLoadingFragment extends Fragment {
    @Inject
    DataLoadingModel dataLoadingModel;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        FragmentInjectors.inject(this);
    }

    public DataLoadingModel getDataLoadingModel() {
        return dataLoadingModel;
    }
}
