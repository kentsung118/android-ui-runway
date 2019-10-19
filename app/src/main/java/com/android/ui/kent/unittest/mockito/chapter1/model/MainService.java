package com.android.ui.kent.unittest.mockito.chapter1.model;

/**
 * Created by Kent on 2018/1/26.
 */

public class MainService {

    private RestService restService;
    private MyPrinter printer;

    public MainService(RestService restService,
            MyPrinter printer) {
        this.restService = restService;
        this.printer = printer;
    }

    public void doSomething() {
        String s = restService.getSomething();
        printer.print(s.toUpperCase());
    }

}
