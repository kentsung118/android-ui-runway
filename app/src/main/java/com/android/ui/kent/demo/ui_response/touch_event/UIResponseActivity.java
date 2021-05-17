package com.android.ui.kent.demo.ui_response.touch_event;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by Kent on 2018/5/3.
 */

public class UIResponseActivity extends BaseActivity {

    public static final String TAG = "UIResponse";
    MyButton myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_response);

        ButterKnife.bind(this);
        initToolbar();

        this.myButton = (MyButton) findViewById(R.id.button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG," UIResponseActivity >>> MyButton 觸發了 OnClick()");
                Toast.makeText(UIResponseActivity.this, "MyButton 觸發了 OnClick()", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("UI Response");
        this.enableBackButton();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG,">>> UIResponseActivity 中调用 dispatchTouchEvent()");
        Log.i(TAG,">>> UIResponseActivity super.dispatchTouchEvent()默认返回true");
        Log.i(TAG,"--------------------------------------------------");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG,">>> UIResponseActivity 中调用  onTouchEvent()--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG,">>> UIResponseActivity 中调用  onTouchEvent()--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG,">>> UIResponseActivity 中调用  onTouchEvent()--->ACTION_UP");
            default:
                break;
        }
        Log.i(TAG,"super.onTouchEvent()默认返回false 表示未消费事件");
        Log.i(TAG,"--------------------------------------------------");
        return super.onTouchEvent(event);
    }

    @Override
    public void onUserInteraction() {
        Log.i(TAG,">>> UIResponseActivity 中调用 onUserInteraction()");
        Log.i(TAG,"--------------------------------------------------");
        super.onUserInteraction();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, UIResponseActivity.class);
        activity.startActivity(intent);
    }
}
