package com.android.ui.kent.demo.framwork.retrofit;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 最終的請求拼裝類
 * Created by songzhukai on 2019-11-15.
 */
class RequestBuilder {

    private final String method;
    private final HttpUrl baseUrl;
    private final String relativeUrl;
    private HttpUrl.Builder urlBuilder;
    private FormBody.Builder formBuilder;
    private Request.Builder requestBuilder;

    public RequestBuilder(String method, HttpUrl baseUrl, String relativeUrl, boolean hasBody) {
        this.method = method;
        this.baseUrl = baseUrl;
        this.relativeUrl = relativeUrl;

        //初始化請求
        requestBuilder = new Request.Builder();
        //根據是否有請求實體 form 表單建構者
        if (hasBody) formBuilder = new FormBody.Builder();
    }

    /**
     * 拼接Query參數
     **/
    void addQueryParam(String name, String value) {
        if (relativeUrl != null) {
            // baseUrl + 方法註解 BASE_URL
            urlBuilder = baseUrl.newBuilder(relativeUrl);
            if (urlBuilder == null) {
                throw new IllegalArgumentException("Malformed Url. Base:" + baseUrl + ", Relative:" + relativeUrl);
            }
            //每次請求都實例化重置
            requestBuilder = null;

        }
        urlBuilder.addQueryParameter(name, value);
    }

    /**
     * 拼接Field 參數
     **/
    void addFormField(String name, String value) {
        if(formBuilder != null){
            formBuilder.add(name, value);
        }
    }

    Request build() {
        //定義局部變數. 1.保證每次值不一樣 2.易回收
        HttpUrl url;
        if (urlBuilder != null) {
            url = urlBuilder.build();
        } else {
            url = baseUrl.resolve(relativeUrl);
            if(url == null){
                throw new IllegalArgumentException("Malformed Url. Base:" + baseUrl + ", Relative:" + relativeUrl);
            }
        }

        //如果有請求實體，構造 Form 表單，然後再實例化請求
        RequestBody body = null;
        if(formBuilder!= null){
            body = formBuilder.build();
        }

        //建立完整請求
        return requestBuilder
                .url(url)
                .method(method, body)
                .build();
    }
}
