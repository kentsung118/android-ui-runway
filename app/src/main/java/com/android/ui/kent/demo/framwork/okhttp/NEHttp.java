package com.android.ui.kent.demo.framwork.okhttp;

/**
 * Created by songzhukai on 2019-11-12.
 */
public class NEHttp {

    public static <T, M> void sendJsonRequest(String url, T requestData, Class<M> response, IJsonDataTransforListener jsonDataTransforListener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        CallbackListener callbackListener = new JsonCallbackListener<>(response, jsonDataTransforListener);
        HttpTask httpTask = new HttpTask(url, requestData, httpRequest, callbackListener);
        ThreadPollManager.getInstance().addTask(httpTask);
    }
}
