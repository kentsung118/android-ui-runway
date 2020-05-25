package com.android.ui.kent.demo.mvvm.viewmodel;

import androidx.annotation.NonNull;

/**
 * Created by Kent Song on 2019/3/18.
 */
public class Lcee<T> {
    private Status status;
    private T data;
    private Throwable error;

    Lcee(@NonNull Status status, T data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }

    /**
     * 以下是创建方法
     **/
    public static <T> Lcee<T> content(T data) {
        return new Lcee<>(Status.Content, data, null);
    }

    public static <T> Lcee<T> error(T data, Throwable error) {
        return new Lcee<>(Status.Error, data, error);
    }

    public static <T> Lcee<T> error(Throwable error) {
        return error(null, error);
    }

    public static <T> Lcee<T> empty(T data) {
        return new Lcee<>(Status.Empty, data, null);
    }

    public static <T> Lcee<T> empty() {
        return empty(null);
    }

    public static <T> Lcee<T> loading(T data) {
        return new Lcee<>(Status.Loading, data, null);
    }

    public static <T> Lcee<T> loading() {
        return loading(null);
    }
}
