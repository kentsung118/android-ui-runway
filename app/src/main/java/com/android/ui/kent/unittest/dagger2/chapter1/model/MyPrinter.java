package com.android.ui.kent.unittest.dagger2.chapter1.model;

/**
 * Created by Kent on 2018/1/26.
 */

public class MyPrinter {

    public MyPrinter() {
        System.out.println("MyPrinter constructor init...");
    }

    public void print(String s) {
        System.out.println(s);
    }
}
