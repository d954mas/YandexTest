package com.d954mas.android.yandextest;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
class AndroidModule {

    private final Context context;

    AndroidModule(final Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }

}
