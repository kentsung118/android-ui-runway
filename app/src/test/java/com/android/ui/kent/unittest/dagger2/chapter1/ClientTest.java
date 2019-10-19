package com.android.ui.kent.unittest.dagger2.chapter1;

import com.android.ui.kent.unittest.dagger2.chapter1.di.DaggerTestComponent;
import com.android.ui.kent.unittest.dagger2.chapter1.di.TestComponent;
import com.android.ui.kent.unittest.dagger2.chapter1.di.TestModule;
import com.android.ui.kent.unittest.dagger2.chapter1.model.MainService;
import com.android.ui.kent.unittest.dagger2.chapter1.model.MyPrinter;
import com.android.ui.kent.unittest.dagger2.chapter1.model.RestService;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Kent on 2018/1/26.
 */
public class ClientTest {
    @Inject RestService restService;

    @Inject MyPrinter myPrinter;

    @Inject MainService mainService;

    @Before public void setUp() {
        TestComponent component = DaggerTestComponent.builder()
                .myModule(new TestModule()).build();
        component.inject(this);


    }

    @Test public void testDoSomething() {
        System.out.println("restService= "+restService);
        System.out.println("myPrinter= "+myPrinter);
        System.out.println("mainService= "+mainService);


        when(restService.getSomething()).thenReturn("abc");

        mainService.doSomething();

        verify(myPrinter).print("ABC");
    }

}