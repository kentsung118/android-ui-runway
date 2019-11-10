package com.android.ui.kent.demo.framwork.eventbus;


import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by songzhukai on 2019-11-10.
 */
public class EventBus {

    private static volatile EventBus instance;

    private Map<Object, List<SubscriberMethod>> mCacheMap;


    private EventBus() {
    }

    public static EventBus getInstance() {
        if (instance == null) {
            synchronized ((EventBus.class)) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    public void register(Object obj) {
        //尋找 obj (Activity 所有帶有 Subscrible註解的方法)
        List<SubscriberMethod> list = mCacheMap.get(obj);
        if (list == null) {
            list = findSubscribeMethod(obj);
            mCacheMap.put(obj, list);
        }
    }

    private List<SubscriberMethod> findSubscribeMethod(Object obj) {
        List<SubscriberMethod> list = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Subscrible subscrible = method.getAnnotation(Subscrible.class);
            if (subscrible == null) {
                continue;
            }
            //判斷方法中的參數是否唯一
            Class<?>[] types = method.getParameterTypes();
            if (types.length != 1) {
                Log.e("Error", "Eventbus only accpet one param");
            }
            ThreadMode threadMode = subscrible.threadModel();
            SubscriberMethod subscriberMethod = new SubscriberMethod(method, threadMode, types[0]);
            list.add(subscriberMethod);

        }

        return list;
    }

}
