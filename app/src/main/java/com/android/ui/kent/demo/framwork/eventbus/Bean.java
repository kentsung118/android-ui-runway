package com.android.ui.kent.demo.framwork.eventbus;

/**
 * Created by songzhukai on 2019-11-11.
 */
public class Bean {

    String msg1;
    String mag2;

    public Bean(String msg1, String mag2) {
        this.msg1 = msg1;
        this.mag2 = mag2;
    }

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }

    public String getMag2() {
        return mag2;
    }

    public void setMag2(String mag2) {
        this.mag2 = mag2;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "msg1='" + msg1 + '\'' +
                ", mag2='" + mag2 + '\'' +
                '}';
    }
}
