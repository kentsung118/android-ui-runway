package com.android.ui.kent.demo.rxjava;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by Kent on 2018/1/17.
 */

public class T3 extends Single<String> {

    @Override
    protected void subscribeActual(SingleObserver<? super String> observer) {


        System.out.println("T3 subscribeActual");


        final Disposable disposable = new Disposable() {
            boolean isDispose = false;

            @Override
            public void dispose() {
                System.out.println("T3 dispose");
                isDispose = true;
            }

            @Override
            public boolean isDisposed() {
                System.out.println( "T3 isDisposed");
                return isDispose;
            }
        };

        observer.onSubscribe(disposable);

        System.out.println("T3 onSuccess");
        observer.onSuccess("T3 onSuccess");

//        System.out.println("T3 onError");
//        observer.onError(new Exception("T1 onError"));

    }
}
