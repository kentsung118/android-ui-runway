package com.android.ui.kent.demo.architecture.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.android.ui.kent.R;

import timber.log.Timber;

/**
 * Created by Kent Song on 2018/12/30.
 */
public class FragmentTextView extends AppCompatTextView implements LifecycleObserver {
    public FragmentTextView(Context context) {
        super(context);
        init();
    }

    public FragmentTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FragmentTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTextColor(getContext().getResources().getColor(R.color.main_black));
        setTextSize(24);
        setPadding(0, 200, 0, 0);

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        Timber.d(">> FragmentTextView revicer onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void resume() {
        Timber.d(">> FragmentTextView revicer onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void pause() {
        Timber.d(">> FragmentTextView revicer onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        Timber.d(">> FragmentTextView revicer onSTop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destory() {
        Timber.d(">> FragmentTextView revicer onDestory");
    }
}
