package com.android.ui.kent.demo.recyclerview.util;

/**
 * Created by KentSong on 2018/6/11.
 * 支持焦点、选中记忆
 */
public class SimpleData<T> {

    private T data;

    private boolean isFocused;
    private boolean isSelected;

    public SimpleData(T data) {
        this.data = data;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public T getData() {
        return data;
    }
}
