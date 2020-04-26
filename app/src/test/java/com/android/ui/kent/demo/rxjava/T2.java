package com.android.ui.kent.demo.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Kent on 2018/1/16.
 */

public class T2 extends Observable<Integer> {
    private String TAG = T2.class.getSimpleName();


    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        System.out.println("T2 subscribeActual");

        final Disposable disposable = new Disposable() {
            boolean isDispose = false;

            @Override
            public void dispose() {
                System.out.println( "T2 dispose");
                isDispose = true;
            }

            @Override
            public boolean isDisposed() {
                System.out.println("T2 isDisposed");
                return isDispose;
            }
        };

        observer.onSubscribe(disposable);
        System.out.println("T2 onNext");
        observer.onNext(12345);
        System.out.println("T2 onComplete");
        observer.onComplete();
//        System.out.println("T2 onError");
//        observer.onError(new Exception("T2 onError"));

    }

    public Integer getSomething(){
        return 1;
    }
}
