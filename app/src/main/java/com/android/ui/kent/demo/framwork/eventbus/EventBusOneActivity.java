package com.android.ui.kent.demo.framwork.eventbus;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by songzhukai on 2019-11-11.
 */
public class EventBusOneActivity extends BaseActivity {

    private String TAG = EventBusOneActivity.class.getSimpleName();

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

        EventBus.getInstance().register(this);

        btn1.setText("跳轉 EventTwoActivity");
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("EventBusOne");
        this.enableBackButton();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, EventBusOneActivity.class);
        activity.startActivity(intent);
    }

    @Subscrible(threadModel = ThreadMode.MAIN)
    public void aa(Bean bean) {
        Log.d(TAG, "ThreadMode.MAIN bean ====>" + bean.toString());
        Toast.makeText(this, bean.toString(), Toast.LENGTH_SHORT).show();
    }

    @Subscrible(threadModel = ThreadMode.BACKGROUND)
    public void bb(Bean bean) {
        Log.d(TAG, "ThreadMode.BACKGROUND bean ====>" + bean.toString());
    }

    @OnClick(R.id.btn1)
    public void jump() {
        EventBusTwoActivity.launch(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().remove(this);

    }
}
