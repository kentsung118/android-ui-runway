package com.android.ui.kent.demo.udp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.udp.vo.LinkInfo;
import com.android.ui.kent.demo.udp.vo.PonInfo;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kent Song on 2019/2/18.
 */
public class UdpActivity extends BaseActivity {


    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.btn_send_pon)
    Button btnSend;
    @BindView(R.id.btn_send_live)
    Button btnReciver;
    @BindView(R.id.text_result)
    TextView textResult;

    UdpCommand mPon;
    DeviceManager mDeviceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udp);
        ButterKnife.bind(this);
        setupToolBar();

        mDeviceManager = new DeviceManager();
        mPon = new UdpCommand();
        getLifecycle().addObserver(mDeviceManager);
    }

    private void setupToolBar() {
        this.setupToolbar();
        this.setToolbarTitle(getString(R.string.main_action_udp));
    }

    @OnClick({R.id.btn_send_pon, R.id.btn_send_live, R.id.btn_send_pppoe_get, R.id.btn_send_pppoe_set})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_send_pon:
                mDeviceManager.requestPonInfo(new DeviceManager.PonInfoListener() {
                    @Override
                    public void onDataReceived(PonInfo ponInfo) {
                        textResult.setText(new Gson().toJson(ponInfo));
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(UdpActivity.this, "send pon error", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.btn_send_live:
                mDeviceManager.requestLiveInfo(new DeviceManager.LinkInfoListener() {
                    @Override
                    public void onDataReceived(LinkInfo linkInfo) {
                        textResult.setText(new Gson().toJson(linkInfo));
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(UdpActivity.this, "send live error", Toast.LENGTH_LONG).show();

                    }
                });
                break;
            case R.id.btn_send_pppoe_set:
                mDeviceManager.setPPPoe("s888", "12346");
                break;
            case R.id.btn_send_pppoe_get:
                mDeviceManager.getPPPoE(new DeviceManager.PonInfoListener() {
                    @Override
                    public void onDataReceived(PonInfo ponInfo) {
                        textResult.setText(new Gson().toJson(ponInfo));
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }
                });
                break;
        }
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, UdpActivity.class);
        activity.startActivity(intent);
    }


}
