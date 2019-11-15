package com.android.ui.kent.demo.framwork.retrofit;

import com.android.ui.kent.demo.framwork.retrofit.annotation.Field;
import com.android.ui.kent.demo.framwork.retrofit.annotation.GET;
import com.android.ui.kent.demo.framwork.retrofit.annotation.POST;

import org.junit.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;

/**
 * Created by songzhukai on 2019-11-15.
 */
public class RetrofitTest {

    interface Host {
        @GET("/ip/ipNew")
        Call get(@Field("ip") String ip, @Field("key") String key);

        @POST("/ip/ipNew")
        Call post(@Field("ip") String ip, @Field("key") String key);
    }

    @Test
    public void proxy() {
        Host host = (Host) Proxy.newProxyInstance(Host.class.getClassLoader(), new Class[]{Host.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //獲取方法名稱
                System.out.println("獲取方法名稱 >>> " + method.getName());
                //獲取方法的註解
                GET get = method.getAnnotation(GET.class);
                //獲取方法的註解值
                System.out.println("獲取方法的註解值 >>> " + get.value());
                //獲取方法的參數的註解
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                for (Annotation[] annotations : parameterAnnotations) {
                    System.out.println("獲取方法的參數的註解 >>> " + Arrays.toString(annotations));
                }
                //獲取方法的參數值
                System.out.println("獲取方法的參數值 >>> " + Arrays.toString(args));
                return null;
            }
        });
        host.get("1.2.3.4", "appkey");
    }

    private final String BASE_URL = "http://apis.juhe.cn";
    private final String IP = "144.34.161.97";
    private final String KEY = "e8384b6fb117e7fa4faef010da01466a";

    @Test
    public void test() throws InterruptedException {
        Retrofit retrofit = Retrofit.newBuilder()
                .setBaseUrl(HttpUrl.parse(BASE_URL))
                .build();

        Host host = retrofit.create(Host.class);
        Call call = host.post(IP, KEY);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("onFailure： " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("onResponse： " + response);
                System.out.println("onResponse body： " + response.body().string());
            }
        });

        System.out.println("call.isExecuted()： " + call.isExecuted());
        Thread.sleep(10000);
    }

}