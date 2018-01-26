package com.android.ui.kent.unittest.dagger2.chapter1.di;

import com.android.ui.kent.unittest.dagger2.chapter1.model.MyPrinter;
import com.android.ui.kent.unittest.dagger2.chapter1.model.RestService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Kent on 2018/1/26.
 */

@Module
public class MyModule {
    @Provides @Singleton
    public RestService provideRestService() {
        return new RestService();
    }

    @Provides @Singleton
    public MyPrinter provideMyPrinter() {
        return new MyPrinter();
    }
}