package com.android.ui.kent.demo.architecture.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.android.ui.kent.R;

import timber.log.Timber;

/**
 * Created by Kent Song on 2018/12/30.
 */
public class ActTextView extends AppCompatTextView implements LifecycleObserver {
    public ActTextView(Context context) {
        super(context);
        init();
    }

    public ActTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ActTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTextColor(getContext().getResources().getColor(R.color.main_black));
        setTextSize(24);
        setPadding(0, 100, 0, 0);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        Timber.d(">> ActTextView revicer onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void resume() {
        Timber.d(">> ActTextView revicer onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void pause() {
        Timber.d(">> ActTextView revicer onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        Timber.d(">> ActTextView revicer onSTop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destory() {
        Timber.d(">> ActTextView revicer onDestory");
    }
}
