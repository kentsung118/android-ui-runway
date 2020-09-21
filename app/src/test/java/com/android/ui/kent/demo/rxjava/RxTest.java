package com.android.ui.kent.demo.rxjava;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
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
    public void testRxT1() {
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
                        System.out.println("T2 onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("T2 onNext integer");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e);
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
    public void testRxT3() {
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

    private int count;

    @Test
    public void testUndeliverableException () throws InterruptedException {

//            RxJavaPlugins.setErrorHandler(throwable -> {
//                throwable.printStackTrace();
//            });


        Observable<Integer> values = Observable.create(o -> {

            if(count == 0){
                System.out.println("into flag1");

                final Timer timer = new Timer();
                final TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("o.isDisposed()="+o.isDisposed());
                        o.onError(new Exception("TimeOut"));
                        timer.cancel(); // stop timer after execution
                    }
                };
                timer.schedule(task, 1000); // schedule task with delay of 1000ms
                o.onNext(1);
                o.onComplete();

                System.out.println("o.isDisposed()="+o.isDisposed());

            } else {
                System.out.println("into flag2");
                System.out.println("o.isDisposed()="+o.isDisposed());
                o.onNext(1);
                o.onComplete();
                System.out.println("o.isDisposed()="+o.isDisposed());
            }
            count ++;


//            o.onNext(1);
//            System.out.println("o.isDisposed()="+o.isDisposed());
//            o.onNext(2);
//            o.onComplete();
//            System.out.println("o.isDisposed()="+o.isDisposed());


        });
        System.out.println("subscribe1");

        values
                .subscribe(
                        System.out::println,
                        e -> System.out.println("Error: " + e.getMessage()),
                        () -> System.out.println("Completed")
                );
//        System.out.println("subscribe2");

//        values
//                .subscribe(
//                        System.out::println,
//                        e -> System.out.println("Error: " + e.getMessage()),
//                        () -> System.out.println("Completed")
//                );


        Thread.sleep(5000);
    }

    Disposable md;
    @Test
    public void testRx2() {
        LeUserInfoObservable observable = new LeUserInfoObservable();


        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println( "onSubscribe....Disposable="+d);
                md = d;
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println( "onNext="+integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println( "Error: " + e.getMessage());

            }

            @Override
            public void onComplete() {
                System.out.println( "onComplete=");
                md.dispose();

            }
        });

//        observable.subscribe(
//                System.out::println,
//                e -> System.out.println("Error: " + e.getMessage()),
//                () -> System.out.println("Completed")
//        );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
