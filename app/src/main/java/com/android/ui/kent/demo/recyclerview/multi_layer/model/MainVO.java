package com.android.ui.kent.demo.recyclerview.multi_layer.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Kent Song on 2018/11/30.
 */
public class MainVO {

    public MainVO(String title, int type) {
        this.title = title;
        this.type = type;
    }

    private String title;
    private Object object;
    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getType() {
        return type;
    }
}
