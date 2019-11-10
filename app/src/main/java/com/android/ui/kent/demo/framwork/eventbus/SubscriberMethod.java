package com.android.ui.kent.demo.framwork.eventbus;

import java.lang.reflect.Method;

/**
 * Created by songzhukai on 2019-11-10.
 */
public class SubscriberMethod {

    //方法本身
    private Method method;

    //線程模式
    private ThreadMode threadMode;

    //方法的參數
    private Class<?> type;

    public SubscriberMethod(Method method, ThreadMode threadMode, Class<?> type) {
        this.method = method;
        this.threadMode = threadMode;
        this.type = type;
    }

    public Method getMethod() {
        return method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public Class<?> getType() {
        return type;
    }
}
