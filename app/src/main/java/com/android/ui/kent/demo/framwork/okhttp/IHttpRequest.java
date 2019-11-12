package com.android.ui.kent.demo.framwork.okhttp;

/**
 * Created by songzhukai on 2019-11-12.
 */
public interface IHttpRequest {

    void setUrl(String url);

    void setData(byte[] bytes);

    void setListener(CallbackListener callBackListener);

    void execute();
}
