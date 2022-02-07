package com.kent.android.slim.sample.framework.okhttp;


import android.util.Log;

import com.kent.android.slim.sample.BuildConfig;
import com.kent.android.slim.sample.Constants;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Kent on 2017/4/6.
 */

public class GitHubApiKt {

    public static final String BASE_URL = "https://api.github.com/";
    private static Cache mCache = new Cache(new File(Constants.INSTANCE.getCACHE_OK_PATH()), 50L * 1024L * 1024L);


    public static OkHttpClient getOKhttpClient() {

        OkHttpClient.Builder httpBuilder = getTrustAllBuilder();
        httpBuilder.readTimeout(6, TimeUnit.SECONDS);
        httpBuilder.connectTimeout(6, TimeUnit.SECONDS);
        httpBuilder.cache(mCache);

        //http header setting
        httpBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.d("kent flag", "kent flag intercept1 tag = " + request.tag());
                Log.d("kent flag", "kent flag intercept1 tag2 = " + request.tag(String.class));


                Request request2 = chain.request().newBuilder()
                        .tag(Integer.class, 999)
                        .cacheControl(new CacheControl.Builder()
                                .noCache()
//                                .maxStale(365, TimeUnit.DAYS)
                                .build())
                        .build();
                Response response = chain.proceed(request2);
                Log.d("kent flag", "kent flag is cacheResp = " + (response.cacheResponse() != null));

                return response;
            }
        });

        httpBuilder.addNetworkInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.d("kent flag", "kent flag #NetworkInterceptor tag = " + request.tag());
//                Log.d("kent flag", "kent flag #intercept2 tag2 = " + request.tag(String.class));
//                Log.d("kent flag", "kent flag #intercept2 tag3 = " + request.tag(Integer.class));

                Response response = chain.proceed(request);


                response = response.newBuilder()
                        .header("Cache-Control", "max-age=30").build();

                return response;
            }
        });


        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpBuilder.addInterceptor(interceptor);
        }
        return httpBuilder.build();
    }

    public static OkHttpClient getCacheOnlyOKhttpClient() {

        OkHttpClient.Builder httpBuilder = getTrustAllBuilder();
        httpBuilder.readTimeout(6, TimeUnit.SECONDS);
        httpBuilder.connectTimeout(6, TimeUnit.SECONDS);
        httpBuilder.cache(mCache);

        //http header setting
        httpBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
//                        .cacheControl(new CacheControl.Builder()
//                                .onlyIfCached()
//                                .maxStale(10, TimeUnit.MINUTES)
//
//                                .build())
//                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Response response = chain.proceed(request);
                Log.d("kent flag", "re write cache only");
                Log.d("kent flag", "kent flag is cacheResp = " + (response.cacheResponse() != null));
                return response;
            }
        });

        return httpBuilder.build();
    }


    public static OkHttpClient.Builder getTrustAllBuilder() {
        //信任所有憑證
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            });
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
