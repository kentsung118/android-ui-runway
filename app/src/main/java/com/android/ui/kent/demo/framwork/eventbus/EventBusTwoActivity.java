package com.android.ui.kent.demo.framwork.eventbus;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by songzhukai on 2019-11-11.
 */
public class EventBusTwoActivity extends BaseActivity {

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

        btn1.setText("發送 Event");
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("EventBusTwo");
        this.enableBackButton();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, EventBusTwoActivity.class);
        activity.startActivity(intent);
    }

    @OnClick(R.id.btn1)
    public void test() {
        EventBus.getInstance().post(new Bean("bamboo", "hello world"));
    }
}
