package com.d954mas.android.yandextest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d954mas.android.yandextest.R;
import com.d954mas.android.yandextest.models.DataLoadingModel;

//при отсутствие интернета, показываем этот фрагмент
public class InternetErrorFragment extends Fragment {
    DataLoadingModel dataLoadingModel;

    public void setDataLoadingModel(DataLoadingModel dataLoadingModel) {
        this.dataLoadingModel = dataLoadingModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.internet_error_fragment,container,false);
        if(dataLoadingModel!=null){
            view.findViewById(R.id.reconnect_button).setOnClickListener(v-> dataLoadingModel.loadData());
        }else{
            throw new RuntimeException("must have dataLoadingModel");
        }
        return view;
    }
}
