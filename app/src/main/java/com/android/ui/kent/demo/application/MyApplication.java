package com.android.ui.kent.demo.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import timber.log.Timber;

/**
 * Created by Kent on 2016/10/5.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize((int) Runtime.getRuntime().maxMemory() / 8);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.threadPoolSize(3);
        config.imageDownloader(new BaseImageDownloader(context,
                BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT, 30000));

        config.memoryCache(new WeakMemoryCache());
        config.writeDebugLogs(); // Remove for release app

        ImageLoader.getInstance().init(config.build());
        Timber.plant(new Timber.DebugTree());
    }

    public static DisplayImageOptions ImageLoaderOptions = new DisplayImageOptions.Builder()
            .resetViewBeforeLoading(true)
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

}
