package com.android.ui.kent.demo.framwork.retrofit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by songzhukai on 2019-11-15.
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Query {
    /** The query parameter name. */
    String value();

    /**
     * Specifies whether the parameter {@linkplain #value() name} and value are already URL encoded.
     */
    boolean encoded() default false;
}
