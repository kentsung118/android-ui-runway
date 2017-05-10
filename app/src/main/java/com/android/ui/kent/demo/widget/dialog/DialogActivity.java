package com.android.ui.kent.demo.widget.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

/**
 * Created by Kent on 2017/5/10.
 */

public class DialogActivity extends BaseActivity {

    @BindView(R.id.button1) Button button1;
    @BindView(R.id.button2) Button button2;
    @BindView(R.id.button3) Button button3;
    @BindView(R.id.button4) Button button4;
    @BindView(R.id.button5) Button button5;
    @BindView(R.id.button6) Button button6;
    @BindView(R.id.button7) Button button7;
    @BindView(R.id.button8) Button button8;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.enableBackButton();
        this.setToolbarTitle("Dialog Sample");
    }

    @OnClick({
            R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8
    }) public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                DialogHelper.showAlert(this, "Alert 只有title");
                break;
            case R.id.button2:
                DialogHelper.showAlert(this, "Alert 有title", "也有 message");
                break;
            case R.id.button3:
                //直接用AlertDialog 自己組流程也許比較好
                //DialogHelper.getCommonYesNoDialog(this, "測試YesNoDialog", "msg", null).show();
                break;
            case R.id.button4:
                //DialogHelper.getCommonYesNoDialog(this, "測試YesNoDialog", "msg",
                //        new DialogInterface.OnClickListener() {
                //            @Override public void onClick(DialogInterface dialog, int which) {
                //                Toast.makeText(DialogActivity.this, "選了確認", Toast.LENGTH_SHORT).show();
                //            }
                //        }, new DialogInterface.OnClickListener() {
                //            @Override public void onClick(DialogInterface dialog, int which) {
                //                Toast.makeText(DialogActivity.this, "選了取消", Toast.LENGTH_SHORT).show();
                //            }
                //    }).show();
                break;
            case R.id.button5:
                break;
            case R.id.button6:
                break;
            case R.id.button7:
                break;
            case R.id.button8:
                break;
        }
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, DialogActivity.class);
        activity.startActivity(intent);
    }
}
