package com.android.ui.kent.demo.reflect_ioc.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) //該註解作用在屬性
@Retention(RetentionPolicy.RUNTIME) // 在運行時期通過反射技術，獲取該註解的值
public @interface InjectView {
    int value();
}
