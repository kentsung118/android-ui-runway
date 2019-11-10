package com.android.ui.kent.demo.framwork.eventbus;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by songzhukai on 2019-11-10.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscrible {

    ThreadMode threadModel() default ThreadMode.MAIN;
}
