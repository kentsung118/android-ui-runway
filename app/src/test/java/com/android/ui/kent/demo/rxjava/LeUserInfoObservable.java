package com.android.ui.kent.demo.rxjava;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by songzhukai on 2020/6/3.
 */
public class LeUserInfoObservable extends Observable<Integer> {

    int count;

    @Override
    protected void subscribeActual(Observer<? super Integer> o) {

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

        o.onSubscribe(disposable);



        if(count == 0){
            System.out.println("into flag1");

            final Timer timer = new Timer();
            final TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("into flag3");
                    System.out.println("o.isDisposed()="+disposable.isDisposed());
                    o.onError(new java.net.SocketTimeoutException("TimeOut"));
                    timer.cancel(); // stop timer after execution
                }
            };
            timer.schedule(task, 1000); // schedule task with delay of 1000ms
            o.onNext(1);
            o.onComplete();
//            disposable.dispose();

            System.out.println("o.isDisposed()="+disposable.isDisposed());

        } else {
            System.out.println("into flag2");
            System.out.println("o.isDisposed()="+disposable.isDisposed());
            o.onNext(1);
            o.onComplete();
            System.out.println("o.isDisposed()="+disposable.isDisposed());
        }
        count ++;


    }
}
