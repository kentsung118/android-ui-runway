package com.android.ui.kent.unittest.dagger2.chapter1.di;

import com.android.ui.kent.unittest.dagger2.chapter1.ClientTest;
import com.android.ui.kent.unittest.dagger2.chapter1.model.MainService;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by Kent on 2018/1/26.
 */

@Singleton
@Component(modules = MyModule.class)
public interface MyComponent {
    MainService mainService();

    void inject(ClientTest clientTest);
}
