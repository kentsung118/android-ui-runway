package com.android.ui.kent.demo.framwork.okhttp;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.framwork.eventbus.EventBus;
import com.android.ui.kent.demo.framwork.okhttp.vo.Bean;

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
 * Created by songzhukai on 2019-11-12.
 */
public class OkHttpActivity extends BaseActivity {

    private String TAG = OkHttpActivity.class.getSimpleName();

    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.btn1)
    Button btn1;

    private String url = "http://japi.juhe.cn/charconvert/change.from?text=%E7%AB%B9%E5%AD%90&type=2&key=7af56c5c3bc0a5019dbea3e223ba9dc0";
    private String errorUrl = "http://xxxxxxxx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this);

        initToolbar();

        EventBus.getInstance().register(this);

        btn1.setText("发送网路请求");
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("OkHttp Framework");
        this.enableBackButton();
    }

    @OnClick(R.id.btn1)
    public void send() {
        sendRequest();
    }


    private void sendRequest() {
        NEHttp.sendJsonRequest(url, "", Bean.class, new IJsonDataTransforListener<Bean>() {
            @Override
            public void onSuccess(Bean m) {
                Log.d(TAG, "=======> " + m.toString());
                Toast.makeText(OkHttpActivity.this, m.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "=======> onFailure");
                Toast.makeText(OkHttpActivity.this, "onFailure：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, OkHttpActivity.class);
        activity.startActivity(intent);
    }


}
