package com.android.ui.kent.demo.framwork.retrofit;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by songzhukai on 2019-11-15.
 */
public class OkHttpCall implements Call {

    private ServiceMethod mServiceMethod;
    private Object[] atgs;
    private Call rawCall;

    public OkHttpCall(ServiceMethod serviceMethod, Object[] args) {
        mServiceMethod = serviceMethod;
        this.atgs = args;
        this.rawCall = serviceMethod.toCall(args);
    }

    @Override
    public Request request() {
        return rawCall.request();
    }

    @Override
    public Response execute() throws IOException {
        return rawCall.execute();
    }

    @Override
    public void enqueue(Callback responseCallback) {
        System.out.println("OkHttpCall enqueue");
        rawCall.enqueue(responseCallback);
    }

    @Override
    public void cancel() {
        rawCall.cancel();
    }

    @Override
    public boolean isExecuted() {
        return rawCall.isExecuted();
    }

    @Override
    public boolean isCanceled() {
        return rawCall.isCanceled();
    }

    @Override
    public Call clone() {
        return rawCall.clone();
    }
}
