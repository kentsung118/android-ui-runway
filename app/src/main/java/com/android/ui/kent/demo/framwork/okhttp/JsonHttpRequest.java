package com.android.ui.kent.demo.framwork.okhttp;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by songzhukai on 2019-11-12.
 */
public class JsonHttpRequest implements IHttpRequest {

    private static String TAG = JsonHttpRequest.class.getSimpleName();

    private String url;
    private byte[] data;
    private CallbackListener mCallbackListener;
    private HttpURLConnection urlConnection;


    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] bytes) {
        this.data = bytes;
    }

    @Override
    public void setListener(CallbackListener callBackListener) {
        this.mCallbackListener = callBackListener;
    }

    @Override
    public void execute() {
        //访问请求具体操作
        URL url;
        try {
            url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(6000);
            urlConnection.setUseCaches(false);
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setReadTimeout(6000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.connect();
            //------------使用字节流发送数据 ----------------
            OutputStream out = urlConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            bos.write(data);
            bos.flush();
            out.close();
            bos.close();
            //-----------字节流写入数据-------------
            int httpCode = urlConnection.getResponseCode();
            Log.e(TAG, "httpCode = " + httpCode);
            if (httpCode == HttpURLConnection.HTTP_OK) {
                InputStream in = urlConnection.getInputStream();
                mCallbackListener.onSuccess(in);
            } else {
                mCallbackListener.onFailure(new Exception("请求失败 httpCode=" + httpCode));
            }
        } catch (Exception e) {
            e.printStackTrace();
            mCallbackListener.onFailure(e);
        } finally {
            urlConnection.disconnect();
        }
    }
}
