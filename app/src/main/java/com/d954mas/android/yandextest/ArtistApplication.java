package com.d954mas.android.yandextest;

import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class ArtistApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {

        super.onCreate();
        component = DaggerApplicationComponent
                .builder()
                .androidModule(new AndroidModule(this))
                .build();

        initImageLoader(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //never called
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                 .showImageOnLoading(R.mipmap.ic_stub) // resource or drawable
                .showImageForEmptyUri(R.mipmap.ic_empty) // resource or drawable
                .showImageOnFail(R.mipmap.ic_error) // resource or drawable
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true).build();

        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.defaultDisplayImageOptions(options);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
       // config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public static ArtistApplication from(Context context) {
        return (ArtistApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}

