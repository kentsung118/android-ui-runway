package com.android.ui.kent.unittest.mockito.chapter1;

import com.android.ui.kent.unittest.mockito.chapter1.model.MainService;
import com.android.ui.kent.unittest.mockito.chapter1.model.MyPrinter;
import com.android.ui.kent.unittest.mockito.chapter1.model.RestService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Kent on 2018/1/26.
 * 沒有使用到 Dagger2 的 UnitTest
 * 使用 @InjectMocks 將 {@link RestService} {@link MyPrinter} 注入至 {@link MainService}
 */
public class ClientTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock RestService restService;

    @Mock MyPrinter myPrinter;

    @InjectMocks MainService mainService;

    @Test public void testDoSomething() {
        when(restService.getSomething()).thenReturn("abc");

        mainService.doSomething();

        verify(myPrinter).print("ABC");
    }



}