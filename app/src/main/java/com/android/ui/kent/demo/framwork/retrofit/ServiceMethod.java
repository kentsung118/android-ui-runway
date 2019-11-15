package com.android.ui.kent.demo.framwork.retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.HttpUrl;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * aop 攔截方法的封裝信息
 * Created by songzhukai on 2019-11-15.
 */
public class ServiceMethod {

    private Call.Factory callFactory;

    private HttpUrl baseUrl;

    private String httpMethod;

    private String relativeUrl;

    private boolean hasBody;

    private ParameterHandler[] mParameterHandler;


    private ServiceMethod(ServiceMethod.Builder builder) {
        this.callFactory = builder.retrofit.getCallFactory();
        this.baseUrl = builder.retrofit.getBaseUrl();
        this.httpMethod = builder.httpMethod;
        this.relativeUrl = builder.relativeUrl;
        this.mParameterHandler = builder.mParameterHandler;
        this.hasBody = builder.hasBody;
    }

    public Call toCall(Object[] args) {
        //拼裝
        RequestBuilder requestBuilder = new RequestBuilder(httpMethod, baseUrl, relativeUrl, hasBody);

        ParameterHandler[] handlers = this.mParameterHandler;
        //檢測自己收集匹配的args
        int argumentCount = args != null ? args.length : 0;
        if (argumentCount != handlers.length) {
            throw new IllegalArgumentException("自己蒐集的參數錯誤");
        }

        for (int i = 0; i < argumentCount; i++) {
            System.out.println("i=" + i + ", args[i] = " + args[i]);
            handlers[i].apply(requestBuilder, args[i].toString());
        }

        return callFactory.newCall(requestBuilder.build());
    }


    static final class Builder {
        final Retrofit retrofit;
        final Method method;
        //方法註解
        final Annotation[] methodAnnotation;
        //方法參數註解
        final Annotation[][] paramaterAnnotationArray;
        private String httpMethod;
        private String relativeUrl;
        private boolean hasBody;
        private ParameterHandler[] mParameterHandler;

        public Builder(Retrofit retrofit, Method method) {
            this.retrofit = retrofit;
            this.method = method;
            this.methodAnnotation = method.getAnnotations();
            this.paramaterAnnotationArray = method.getParameterAnnotations();
        }

        ServiceMethod build() {

            for (Annotation annotation : methodAnnotation) {
                parseMethodAnnotation(annotation);
            }

            int parameterCount = paramaterAnnotationArray.length;
            // 初始化，參數對象組
            mParameterHandler = new ParameterHandler[parameterCount];
            for (int i = 0; i < parameterCount; i++) {
                //獲取每個參數的所有註解
                Annotation[] parameterAnnotations = paramaterAnnotationArray[i];
                //開發者如果沒有用 Restful
                if (parameterAnnotations == null) {
                    throw new IllegalArgumentException("Retrofit annotation not found!");
                }

                //賦值
                mParameterHandler[i] = parseParameter(parameterAnnotations);
            }

            return new ServiceMethod(this);
        }

        private ParameterHandler parseParameter(Annotation[] parameterAnnotations) {
            ParameterHandler result = null;
            for (Annotation annotation : parameterAnnotations) {
                ParameterHandler annotationAction = parseParameterAnnotation(annotation);
                if (annotationAction == null) {
                    continue;
                }
                result = annotationAction;
            }
            return result;
        }

        private ParameterHandler parseParameterAnnotation(Annotation annotation) {
            if (annotation instanceof Query) {
                System.out.println("parseParameterAnnotation Query");
                Query query = (Query) annotation;
                //參數註解裡面的ip
                String name = query.value();
                return new ParameterHandler.Query(name);
            } else if (annotation instanceof Field) {
                System.out.println("parseParameterAnnotation Field");
                Field field = (Field) annotation;
                String name = field.value();
                return new ParameterHandler.Field(name);
            }
            return null;
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof GET) {
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
            } else if (annotation instanceof POST) {
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
            }
        }

        private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {
            //方法的請求方式
            this.httpMethod = httpMethod;
            //方法的註解值("ip/ipNew")
            this.relativeUrl = value;
            this.hasBody = hasBody;
        }
    }


}
