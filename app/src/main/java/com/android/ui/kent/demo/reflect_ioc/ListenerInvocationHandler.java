package com.android.ui.kent.demo.reflect_ioc;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ListenerInvocationHandler implements InvocationHandler {
    private String TAG = ListenerInvocationHandler.class.getSimpleName();

    private Object target; //需要知道切面那個類的方法 (攔截的目標)
    private HashMap<String, Method> map = new HashMap<>();

    private final static long QUICK_EVENT_TIME_SPAN = 1000;
    private long lastClickTime;

    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method 是 android.view.View$OnClickListener.onClick(android.view.View)
        Log.d(TAG, "call proxy invoke(), method=" + method);

        // 將本應該回調的onClick / onLongClick 方法攔截，而去執行開發者定義的 abc/xyz 方法
        if (target != null) {
            //假如是 onClick()
            String methodName = method.getName();

            //可以考慮阻塞1秒按多次點擊事件
            long timeSpan = System.currentTimeMillis() - lastClickTime;
            if (timeSpan < QUICK_EVENT_TIME_SPAN) {
                Log.e("點擊阻塞，防止誤點", String.valueOf(timeSpan));
                return null;
            }
            lastClickTime = System.currentTimeMillis();

            Method developerCustomMethod = map.get(methodName); //攔截target 回調方法，重新附值
            // developerCustomMethod 是 com.android.ui.kent.demo.reflect_ioc.ReflectIocActivity.onIocButtonClick(android.view.View)
            Log.d(TAG, "call proxy invoke(), developerCustomMethod=" + developerCustomMethod);
            if (developerCustomMethod != null) {
                if (developerCustomMethod.getGenericParameterTypes().length == 0) {
                    return developerCustomMethod.invoke(target);
                }
                return developerCustomMethod.invoke(target, args);
            }
        }

        return null;
    }

    /**
     * 攔截回調方法，執行開發者自定義方法
     *
     * @param callBackListener 如：onClick()
     * @param method           如：abc() / click()
     */
    public void add(String callBackListener, Method method) {
        map.put(callBackListener, method);
    }
}
