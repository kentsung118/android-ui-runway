package com.android.ui.kent.demo.eventbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kent on 2017/3/22.
 */

public class EventBusActivity extends BaseActivity {

    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.btn1)
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this);

        initToolbar();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("EventBus");
        this.enableBackButton();
    }


    @Override
    protected void onStart() {
        super.onStart();
        //註冊 EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //反註冊 EventBus
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn1)
    public void onClick() {

        int num = (int) Math.floor(Math.random() * 3000);
        //發布者
        EventBus.getDefault().post(new MessageEvent("測試title", "測試 message:" + num));
    }


    //訂閱者
    //ThreadMode.POSTING：默认使用该模式，表示该方法会在当前发布事件的线程执行
    //ThreadMode.MAIN：表示会在UI线程中执行
    //ThreadMode.BACKGROUND：若当前线程非UI线程则在当前线程中执行，否则加入后台任务队列，使用线程池调用
    //ThreadMode.ASYNC：加入后台任务队列，使用线程池调用
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Log.d("tester", "thread = " + Thread.currentThread().getName() + " ,onMessageEvent event.msg = " + event.msg);
        text1.setText(event.msg);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent2(MessageEvent event) {
        Log.d("tester", "thread = " + Thread.currentThread().getName() + " ,onMessageEvent event.msg = " + event.msg);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent3(MessageEvent event) {
        Log.d("tester", "thread = " + Thread.currentThread().getName() + " ,onMessageEvent event.msg = " + event.msg);
    }


    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, EventBusActivity.class);
        activity.startActivity(intent);
    }


}
