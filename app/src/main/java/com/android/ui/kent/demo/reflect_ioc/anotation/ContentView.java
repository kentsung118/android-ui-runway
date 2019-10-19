package com.android.ui.kent.demo.reflect_ioc.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) //該註解作用在類之上
@Retention(RetentionPolicy.RUNTIME) // 在運行時期通過反射技術，獲取該註解的值
public @interface ContentView {

    //獲取布局的 Layout res_id 常量值
    int value();
}
