package com.android.ui.kent.demo.network;

import com.android.ui.kent.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kent on 2017/4/6.
 */

public class GitHub_API {

    public static final String BASE_URL = "https://api.github.com/";

    public static GitHubService getGithubService() {

        OkHttpClient.Builder httpBuilder = new OkHttpClient().newBuilder();
        httpBuilder.readTimeout(10, TimeUnit.SECONDS);
        httpBuilder.connectTimeout(10, TimeUnit.SECONDS);
        httpBuilder.cache(null);

        //http header setting
        httpBuilder.addInterceptor(new Interceptor() {
            @Override public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("header1", "value1")
                        .addHeader("header2", "value2").build();
                return chain.proceed(request);
            }
        });

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpBuilder.addInterceptor(interceptor);
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(GitHubService.class);
    }




}
