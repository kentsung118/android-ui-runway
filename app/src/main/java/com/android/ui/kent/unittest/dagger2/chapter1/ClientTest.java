package com.android.ui.kent.unittest.dagger2.chapter1;

import com.android.ui.kent.unittest.dagger2.chapter1.model.MainService;
import com.android.ui.kent.unittest.dagger2.chapter1.model.MyPrinter;
import com.android.ui.kent.unittest.dagger2.chapter1.model.RestService;
import javax.inject.Inject;

/**
 * Created by Kent on 2018/1/26.
 */

public class ClientTest {

    @Inject RestService restService;

    @Inject MyPrinter myPrinter;

    @Inject MainService mainService;


    public static void main(String[] args){
        //TestComponent component = DaggerTestComponent.builder()
        //        .myModule(new TestModule()).build();
        //component.inject(this);
        //
        //
        //    System.out.println("restService= "+restService);
        //    System.out.println("myPrinter= "+myPrinter);
        //    System.out.println("mainService= "+mainService);
        //
        //
        //    when(restService.getSomething()).thenReturn("abc");
        //
        //    mainService.doSomething();
        //
        //    verify(myPrinter).print("ABC");
        //}



    }


}
