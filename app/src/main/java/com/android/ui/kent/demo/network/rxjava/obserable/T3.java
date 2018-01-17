package com.android.ui.kent.demo.network.rxjava.obserable;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Kent on 2018/1/17.
 */

public class T3 {

    public Observable<String> getObserverable(){

        Observable<String> t3 = new Observable<String>(){
            @Override protected void subscribeActual(Observer<? super String> observer) {

            }
        };

        return t3;

    }


}
