package com.android.ui.kent.demo.framwork.retrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by songzhukai on 2019-11-15.
 */
public class Retrofit {

    //緩存集合，避免重複解讀佟一個方法，浪費性能
    private final Map<Method, ServiceMethod> mServiceMethodCacheMap = new ConcurrentHashMap<>();

    //請求地址
    private HttpUrl baseUrl;

    //請求
    private Call.Factory callFactory;


    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //攔截某方法的內容：方法名、方法的註解值、方法的參數名、參數值
                //將這些信息封裝到 ServiceMethod
                ServiceMethod serviceMethod = loadServiceMethod(method);
                //創建 okhttp 請求對象
                return new OkHttpCall(serviceMethod, args);
            }
        });
    }

    private ServiceMethod loadServiceMethod(Method method) {
        //從緩存集合中找
        ServiceMethod result = mServiceMethodCacheMap.get(method);
        if (result != null) return result;
        //同步鎖
        synchronized (mServiceMethodCacheMap){
            result = mServiceMethodCacheMap.get(method);
            if(result == null){
                result = new ServiceMethod.Builder(this, method).build();
                mServiceMethodCacheMap.put(method, result);
            }
        }
        return result;
    }


    private Retrofit(Builder builder) {
        baseUrl = builder.baseUrl;
        callFactory = builder.callFactory;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private HttpUrl baseUrl;
        private Call.Factory callFactory;

        private Builder() {
        }

        public Builder setBaseUrl(HttpUrl val) {
            baseUrl = val;
            return this;
        }

        public Builder setCallFactory(Call.Factory val) {
            callFactory = val;
            return this;
        }

        public Retrofit build() {
            if (baseUrl == null) {
                throw new NullPointerException("Base URL required");
            }

            if (callFactory == null) {
                callFactory = new OkHttpClient();
            }

            return new Retrofit(this);
        }
    }

    public HttpUrl getBaseUrl(){
        return baseUrl;
    }

    public Call.Factory getCallFactory(){
        return callFactory;
    }
}
