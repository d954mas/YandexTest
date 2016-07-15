package com.d954mas.android.yandextest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private final Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    public FragmentManager provideFragmentManager() {
        return fragment.getChildFragmentManager();
    }

}
