package com.android.ui.kent.demo.network.rxjava.sample;

import com.android.ui.kent.demo.network.rxjava.sample.retrofit.ThirdPartyComm;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.mock;

/**
 * Created by Kent on 2018/1/17.
 */

public class DemoRxJava {

    //建立2個主線流程 ( 搭配模擬 Dialog 事件)

    /**
     * 1. Observable ( 交易發起)
     * Subscribe ( 監聽交易結果 )
     * <p>
     * ## 以新光重慶第三方支付例子
     * start --> 交易發起 API --> 。成功   -->  紀錄交易結果  -->  結束
     * --> 。待驗證 -->  等待檢核 Dialog --> Observable (等待檢核)
     * --> 。失敗   -->  例外錯誤 Dialog --> Observable (錯誤處理)
     */


    public static void main(String[] args) throws Exception {

        ThirdPartyComm comm = mock(ThirdPartyComm.class);
        //when(comm.orderProcess(new MockReq())).thenReturn("first");


        final String TAG = "";


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onComplete();
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext : " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError : " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });

    }


}
