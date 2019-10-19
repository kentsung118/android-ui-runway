package com.android.ui.kent.demo.reflect_ioc.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    // 1、監聽方法
    String listenerSetter();

    // 2、監聽接口
    Class<?> listenerType();

    // 3、觀察到用戶行為後，告知 Callback(回調) 方法
    String callBackListener();
}
