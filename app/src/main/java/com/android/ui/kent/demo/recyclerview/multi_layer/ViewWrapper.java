package com.android.ui.kent.demo.recyclerview.multi_layer;

import android.view.View;

public class ViewWrapper {

    private View mView;

    public ViewWrapper(View target) {
        mView = target;
    }

    public int getWidth() {
        return mView.getLayoutParams().width;
    }

    public void setWidth(int width) {
        mView.getLayoutParams().width = width;
        mView.requestLayout();
    }

    public int getHeight() {
        return mView.getLayoutParams().height;
    }

    public void setHeight(int height) {

        mView.getLayoutParams().height = height;
        mView.requestLayout();
    }
}

