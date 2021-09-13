package com.android.ui.kent.demo.application;

import com.android.ui.kent.database.greendao.DaoMaster;
import com.android.ui.kent.database.greendao.DaoSession;
import com.android.ui.kent.demo.mvvm.di.AppComponent;
import com.android.ui.kent.demo.mvvm.di.AppModule;
import com.android.ui.kent.demo.mvvm.di.DaggerAppComponent;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import timber.log.Timber;

/**
 * Created by Kent on 2016/10/5.
 */

public class MyApplication extends MultiDexApplication {

    private static AppComponent mAppComponent;
    private static DaoSession mDaoSession;
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MultiDex.install(this);
        initImageLoader(getApplicationContext());
        Fresco.initialize(this);

        Stetho.initializeWithDefaults(this);
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
//        initGreenDao();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
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

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "greendao_test.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
}
