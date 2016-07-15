package com.d954mas.android.yandextest;

import com.d954mas.android.yandextest.activities.ArtistGenreListActivity;
import com.d954mas.android.yandextest.fragments.ArtistsFragment;
import com.d954mas.android.yandextest.fragments.DataLoadingFragment;
import com.d954mas.android.yandextest.fragments.GenresFragment;
import com.d954mas.android.yandextest.fragments.TabFragment;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = AndroidModule.class)
public interface ApplicationComponent {
    void inject(GenresFragment f);

    void inject(DataLoadingFragment f);

    void inject(ArtistsFragment f);

    TabFragment.Component plus(FragmentModule m);

    void inject(ArtistGenreListActivity a);
}
