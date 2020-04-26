package com.android.ui.kent.demo.rxjava;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by Kent on 2018/1/17.
 */

public class T4 extends Single<String> {

    @Override
    protected void subscribeActual(SingleObserver<? super String> observer) {


        System.out.println("T4 subscribeActual");


        final Disposable disposable = new Disposable() {
            boolean isDispose = false;

            @Override
            public void dispose() {
                System.out.println("T4 dispose");
                isDispose = true;
            }

            @Override
            public boolean isDisposed() {
                System.out.println( "T4 isDisposed");
                return isDispose;
            }
        };

        observer.onSubscribe(disposable);

        System.out.println("T4 onSuccess");
        observer.onSuccess("T4 onSuccess!");

//        System.out.println("T4 onError");
//        observer.onError(new Exception("T4 onError"));

    }
}
