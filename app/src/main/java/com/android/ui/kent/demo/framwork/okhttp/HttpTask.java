package com.android.ui.kent.demo.framwork.okhttp;

import com.google.gson.Gson;

import androidx.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by songzhukai on 2019-11-12.
 */
public class HttpTask<T> implements Runnable, Delayed {

    private IHttpRequest request;

    public HttpTask(String url, T requestData, IHttpRequest httpRequest, CallbackListener listener) {
        this.request = httpRequest;
        httpRequest.setUrl(url);
        httpRequest.setListener(listener);
        String content = new Gson().toJson(requestData);
        try {
            httpRequest.setData(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        //执行具体的网路操作
        try {
            this.request.execute();
        } catch (Exception e) {

            ThreadPollManager.getInstance().addDelayTask(this);
        }
    }

    private long delayTime;
    private int retryCount;
    private boolean isRetryCount = true;

    public long getDelayTime() {
        return delayTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis() + delayTime;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public boolean isRetryCount() {
        return isRetryCount;
    }

    @Override
    public long getDelay(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NonNull Delayed o) {
        return 0;
    }
}
