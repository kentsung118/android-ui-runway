package com.android.ui.kent.demo.eventbus;

/**
 * Created by Kent on 2017/3/22.
 */

public class MessageEvent {

    public String title;
    public String msg;

    public MessageEvent(String title, String msg) {
        this.title = title;
        this.msg = msg;
    }
}
