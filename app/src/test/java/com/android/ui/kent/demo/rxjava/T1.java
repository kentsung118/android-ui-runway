package com.android.ui.kent.demo.rxjava;

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
        System.out.println("T1 subscribeActual");


            final Disposable disposable = new Disposable() {
                boolean isDispose = false;

                @Override
                public void dispose() {
                    System.out.println("T1 dispose");
                    isDispose = true;
                }

                @Override
                public boolean isDisposed() {
                    System.out.println( "T1 isDisposed");
                    return isDispose;
                }
            };

            observer.onSubscribe(disposable);

            System.out.println("T1 onNext");
            observer.onNext("T1 onNext");
            System.out.println("T1 onComplete");
            observer.onComplete();

//            System.out.println("T1 onError");
//            observer.onError(new Exception("T1 onError"));


    }
}
