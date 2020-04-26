package com.android.ui.kent.demo.rxjava;

import org.junit.Test;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by songzhukai on 2020/4/26.
 */
public class RxTest {

    Disposable disposable;

    @Test
    public void testRxT1(){
        T1 t1 = new T1();
        T2 t2 = new T2();

        t1.doOnNext(s -> {

        }).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return s.equals("T1 onNext");
            }
        }).concatMap(new Function<String, ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> apply(String s) throws Exception {
                return t2;
            }
        })
        .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println( "T2 onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println( "T2 onNext integer");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println( e);
//                disposable.dispose();
            }

            @Override
            public void onComplete() {
//                System.out.println( "T2 onComplete");
                disposable.dispose();
            }
        });


//                .subscribe(new Consumer<Integer>() {
//                @Override
//                public void accept(Integer integer) throws Exception {
//                    System.out.println( "Subscriber integer");
//                }
//            }, new Consumer<Throwable>() {
//                @Override
//                public void accept(Throwable throwable) throws Exception {
//                    System.out.println( "Subscriber throwable");                }
//            }
//        );

    }

    @Test
    public void testRxT3(){
        T3 t3 = new T3();
        T4 t4 = new T4();

        t3.doOnSuccess(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return !s.equals("T3 onSuccess");
            }
        }).concatMap(new Function<String, MaybeSource<String>>() {
            @Override
            public MaybeSource<String> apply(String s) throws Exception {
                return t4.toMaybe();
            }
        }).subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("T4 onSubscribe");

            }

            @Override
            public void onSuccess(String s) {
                System.out.println("T4 onSuccess");

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("T4 onError");

            }

            @Override
            public void onComplete() {
                System.out.println("T4 onComplete");

            }
        });


//                .subscribe(new Consumer<String>() {
//                @Override
//                public void accept(String s) throws Exception {
//
//                }}, new Consumer<Throwable>() {
//                @Override
//                public void accept(Throwable throwable) throws Exception {
//
//                }
//        });
    }
}
