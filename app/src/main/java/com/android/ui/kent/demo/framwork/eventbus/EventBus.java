package com.android.ui.kent.demo.framwork.eventbus;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by songzhukai on 2019-11-10.
 */
public class EventBus {

    private static volatile EventBus instance;

    private Map<Object, List<SubscriberMethod>> mCacheMap;
    private Handler mHandler;


    private EventBus() {
        mCacheMap = new HashMap<>();
        mHandler = new Handler();
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

        // while 的原因因為可能繼承父類？
        while (clazz != null) {
            String name = clazz.getName();

            //系統級類不需解析
            if (name.startsWith("java.") || name.startsWith("javax.")
                    || name.startsWith("android.")) {
                break;
            }

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
            //確認有無父類
            clazz = clazz.getSuperclass();
        }

        return list;
    }

    public void post(Object type) {
        //直接循環 cacheMap 中的方法，找到對應方法進行調用
        Set<Object> set = mCacheMap.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            List<SubscriberMethod> list = mCacheMap.get(obj);
            for (SubscriberMethod subscriberMethod : list) {
                if (subscriberMethod.getType().isAssignableFrom(type.getClass())) {
                    switch (subscriberMethod.getThreadMode()) {
                        case MAIN:
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                // 主 --> 主
                                invoke(subscriberMethod, obj, type);
                            } else {
                                // 子 --> 主
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscriberMethod, obj, type);
                                    }
                                });
                            }
                            break;
                        case BACKGROUND:
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                // 子 --> 主
                                // ExecutorService
                                //TODO 這邊還須在修改
                                invoke(subscriberMethod, obj, type);
                            } else {
                                // 子 -->  子
                                invoke(subscriberMethod, obj, type);
                            }
                            break;
                    }

                }
            }
        }

    }

    private void invoke(SubscriberMethod subscriberMethod, Object obj, Object type) {
        Method method = subscriberMethod.getMethod();
        try {
            method.invoke(obj, type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void remove(Object obj) {
        //解除訂閱
        if (mCacheMap.containsKey(obj)) {
            mCacheMap.remove(obj);
        }
    }


}
