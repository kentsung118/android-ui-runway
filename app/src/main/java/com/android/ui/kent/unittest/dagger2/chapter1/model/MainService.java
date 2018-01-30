package com.android.ui.kent.unittest.dagger2.chapter1.model;

import javax.inject.Inject;

/**
 * Created by Kent on 2018/1/26.
 */

public class MainService {

    private RestService restService;
    private MyPrinter printer;

    @Inject public MainService(RestService restService,
            MyPrinter printer) {
        this.restService = restService;
        this.printer = printer;
        System.out.println("MainService constractor init...");
    }

    public void doSomething() {
        String s = restService.getSomething();
        printer.print(s.toUpperCase());
    }
}
