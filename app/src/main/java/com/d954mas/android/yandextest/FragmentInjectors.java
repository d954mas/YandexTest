package com.d954mas.android.yandextest;

import android.support.v4.app.Fragment;
import com.d954mas.android.yandextest.fragments.ArtistsFragment;
import com.d954mas.android.yandextest.fragments.DataLoadingFragment;
import com.d954mas.android.yandextest.fragments.GenresFragment;
import com.d954mas.android.yandextest.fragments.TabFragment;

public class FragmentInjectors {
    public static void inject(DataLoadingFragment fragment) {
        getApplicationComponent(fragment).inject(fragment);
    }

    public static void inject(GenresFragment fragment) {
        getApplicationComponent(fragment).inject(fragment);
    }

    public static void inject(ArtistsFragment fragment) {
        getApplicationComponent(fragment).inject(fragment);
    }

    public static void inject(TabFragment fragment) {
        getApplicationComponent(fragment).plus(new FragmentModule(fragment)).inject(fragment);
    }

    private static ApplicationComponent getApplicationComponent(Fragment fragment) {
        return ArtistApplication.from(fragment.getContext()).getComponent();
    }

}
