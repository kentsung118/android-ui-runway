package com.android.ui.kent.unittest.dagger2.chapter1.di;

import com.android.ui.kent.unittest.dagger2.chapter1.model.MyPrinter;
import com.android.ui.kent.unittest.dagger2.chapter1.model.RestService;
import org.mockito.Mockito;

/**
 * Created by Kent on 2018/1/26.
 */

public class TestModule extends MyModule {
    @Override
    public MyPrinter provideMyPrinter() {
        return Mockito.mock(MyPrinter.class);
    }

    @Override
    public RestService provideRestService() {
        return Mockito.mock(RestService.class);
    }
}
