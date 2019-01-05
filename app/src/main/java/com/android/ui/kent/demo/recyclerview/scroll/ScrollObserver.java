package com.android.ui.kent.demo.recyclerview.scroll;

import android.support.v7.widget.RecyclerView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by Kent Song on 2019/1/5.
 */
public class ScrollObserver{
    RecyclerView.OnScrollListener scroListener;
    private OnStateListener listener;

    public ScrollObserver() {
        scroListener = new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE && listener != null){
                    listener.onIdle();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        } ;
    }

    public void addObserver(RecyclerView rv){
        rv.addOnScrollListener(scroListener);
    }

    public void rmObserver(RecyclerView rv){
        rv.removeOnScrollListener(scroListener);
    }

    public interface OnStateListener {
        void onIdle();
    }

    public void setListener(OnStateListener listener) {
        this.listener = listener;
    }


    public Observable rxSmoothScroll(RecyclerView rv, int x, int y){
//        return Observable.just(rv)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(new Consumer<RecyclerView>() {
//                    @Override
//                    public void accept(RecyclerView recyclerView) throws Exception {
//                        addObserver(recyclerView);
//                        recyclerView.smoothScrollBy(x,y);
//                    }
//                });


        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {

                rv.addOnScrollListener(scroListener);
                rv.smoothScrollBy(x,y);
                setListener(new OnStateListener() {
                    @Override
                    public void onIdle() {
                        rv.removeOnScrollListener(scroListener);
                        e.onComplete();
                    }
                });

            }
        });
    }


}