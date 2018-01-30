package com.android.ui.kent.unittest.mockito.chapter1.model;

/**
 * Created by Kent on 2018/1/26.
 */

public class RestService {

    public RestService() {
        System.out.println("RestService constructor init...");
    }

    public String getSomething() {
        return "Hello world";
    }
}
