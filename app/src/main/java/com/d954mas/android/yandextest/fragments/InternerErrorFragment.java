package com.d954mas.android.yandextest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d954mas.android.yandextest.R;

/**
 * Created by user on 11.04.2016.
 */
public class InternerErrorFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.internet_error_fragment,container,false);
    }
}
