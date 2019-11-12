package com.android.ui.kent.demo.framwork.okhttp;

/**
 * Created by songzhukai on 2019-11-12.
 */
public interface IJsonDataTransforListener<T> {

    void onSuccess(T m);

    void onFailure(Exception e);

}
