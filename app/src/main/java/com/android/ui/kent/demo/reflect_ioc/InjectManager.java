package com.android.ui.kent.demo.reflect_ioc;

import android.app.Activity;
import android.view.View;
import com.android.ui.kent.demo.reflect_ioc.anotation.ContentView;
import com.android.ui.kent.demo.reflect_ioc.anotation.EventBase;
import com.android.ui.kent.demo.reflect_ioc.anotation.InjectView;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*********

 反射機制

 Api                  |        描述
 ---------------------|--------------------
 getClass             |       獲取類
 ---------------------|--------------------
 getAnnotation(xx.class)|    獲取註解
 ---------------------|--------------------
 getMethod()          |      獲取方法
 ---------------------|--------------------
 invoke(xx,yy)        |      執行方法
 ---------------------|--------------------
 getDeclaredMethods() |     獲取所有方法
 ---------------------|--------------------
 getDeclaredFields()  |     獲取所有屬性
 ---------------------|--------------------
 annotationType()     |     獲取註解的類型
 ---------------------|--------------------
 */

public class InjectManager {

    //幫助子類完成：布局、屬性、點擊事件 Ioc 注入
    public static void inject(Activity activity) {
        injectLayout(activity);
        injectViews(activity);
        injectEvents(activity);
    }

    //布局注入
    private static void injectLayout(Activity activity) {
        //取得當前 Activity
        Class<? extends Activity> clazz = activity.getClass();
        //獲取類之上的註解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            int layoutId = contentView.value();

            //第一種方式
            //activity.setContentView(layoutId);

            try {
                //獲取 setContentView 方法
                Method method = clazz.getMethod("setContentView", int.class);
                //執行 setContentView
                method.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //控件的注入
    private static void injectViews(Activity activity) {
        //取得當前 Activity
        Class<? extends Activity> clazz = activity.getClass();
        //獲取Activity中的所有屬性 (含private)
        Field[] fields = clazz.getDeclaredFields();
        //遍歷
        for (Field field : fields) {
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView != null) {
                int viewId = injectView.value();
                try {
                    //獲取 findViewById 方法
                    Method method = clazz.getMethod("findViewById", int.class);
                    //執行 findViewById
                    Object view = method.invoke(activity, viewId);
                    field.setAccessible(true); //設置訪問私有
                    field.set(activity, view); //賦予屬性 = 執行 findViewById的結果
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //事件的注入
    private static void injectEvents(Activity activity) {
        //取得當前 Activity
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            //方法之上所有的註解
            Annotation[] annotations = method.getAnnotations();
            // 遍歷每個方法上的註解
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    //onClick、onLongClick、onItemClick 的共同特性封裝
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    //拿出包裡的所有特性
                    if (eventBase != null) {
                        // 獲取監聽方法名
                        String listenerSetter = eventBase.listenerSetter();
                        // 獲取監聽接口
                        Class<?> listenerType = eventBase.listenerType();
                        // 獲取回調方法
                        String callBackListener = eventBase.callBackListener();

                        //包，需要一個代理幫我們完成點擊動作

                        //通過 annotationType獲取onClick註解的 value()值{R.id.tv,R.id.btn}
                        try {
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            int[] viewIds = (int[]) valueMethod.invoke(annotation);

                            ListenerInvocationHandler handler =
                                    new ListenerInvocationHandler(activity);
                            handler.add(callBackListener, method);

                            //int[] viewId = onclick.value();
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                    new Class[] { listenerType }, handler);

                            for (int viewId : viewIds) {
                                //獲取view (TextView)
                                View view = activity.findViewById(viewId);
                                //控件類的父類View.class找
                                Method setXXX =
                                        view.getClass().getMethod(listenerSetter, listenerType);
                                //執行方法
                                setXXX.invoke(view, listener);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
