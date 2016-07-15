package com.d954mas.android.yandextest;

import com.d954mas.android.yandextest.activities.ArtistGenreListActivity;

public class ActivityInjectors {

    public static void inject(final ArtistGenreListActivity activity) {
        ArtistApplication.from(activity).getComponent().inject(activity);
    }

}