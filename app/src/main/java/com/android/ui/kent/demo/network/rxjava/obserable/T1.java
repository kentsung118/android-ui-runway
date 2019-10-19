package com.android.ui.kent.demo.network.rxjava.obserable;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Kent on 2018/1/16.
 */

public class T1 extends Observable<String> {

    private String TAG = T1.class.getSimpleName();

    @Override
    protected void subscribeActual(Observer<? super String> observer) {
        Log.d(TAG, "subscribeActual");


        final Disposable disposable = new Disposable() {
            boolean isDispose = false;
            @Override
            public void dispose() {
                Log.d(TAG, "dispose");
                isDispose = true;
            }

            @Override
            public boolean isDisposed() {
                Log.d(TAG, "isDisposed");
                return isDispose;
            }
        };

        observer.onSubscribe(disposable);
        observer.onNext("T1 onNext!");
    }
}
