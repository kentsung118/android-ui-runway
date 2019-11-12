package com.android.ui.kent.demo.framwork.okhttp;

import com.google.gson.Gson;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by songzhukai on 2019-11-12.
 */
public class JsonCallbackListener<T> implements CallbackListener {

    private Class<T> responseClazz;
    private IJsonDataTransforListener mJsonDataTransforListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonCallbackListener(Class<T> responseClazz, IJsonDataTransforListener jsonDataTransforListener) {
        this.responseClazz = responseClazz;
        this.mJsonDataTransforListener = jsonDataTransforListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //将 InputStream 转为对应的 Bean
        Log.e("JsonCallbackListener", "flag1");
        String response = getContent(inputStream);
        Log.e("JsonCallbackListener", "flag2");
        Log.e("JsonCallbackListener", "response =" + response);

        T clazz = new Gson().fromJson(response, responseClazz);
        Log.e("JsonCallbackListener", "flag3");

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mJsonDataTransforListener.onSuccess(clazz);
                Log.e("JsonCallbackListener", "flag4");
            }
        });
    }

    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error" + e.toString());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public void onFailure(Exception e) {
        mJsonDataTransforListener.onFailure(e);
    }
}
