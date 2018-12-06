package com.android.ui.kent.demo.recyclerview.multi_layer.model;

/**
 * Created by Kent Song on 2018/12/1.
 */
public class TextVO {

    private String text;

    public TextVO(String title) {
        this.text = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
