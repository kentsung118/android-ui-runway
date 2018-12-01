package com.android.ui.kent.demo.recyclerview.multi_layer.lookback;

/**
 * Created by Kent Song on 2018/12/1.
 */
public class LookBackVO {

    private String time;
    private String state;
    private String name;

    public LookBackVO(String time, String state, String name) {
        this.time = time;
        this.state = state;
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
