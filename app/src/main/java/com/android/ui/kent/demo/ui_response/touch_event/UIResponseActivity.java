package com.android.ui.kent.demo.ui_response.touch_event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

/**
 * Created by Kent on 2018/5/3.
 */

public class UIResponseActivity extends BaseActivity {

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
                System.out.println(">>> MyButton 觸發了 OnClick()");
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
        System.out.println(">>> UIResponseActivity 中调用 dispatchTouchEvent()");
        System.out.println(">>> super.dispatchTouchEvent()默认返回true");
        System.out.println("--------------------------------------------------");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(">>> UIResponseActivity 中调用  onTouchEvent()--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println(">>> UIResponseActivity 中调用  onTouchEvent()--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println(">>> UIResponseActivity 中调用  onTouchEvent()--->ACTION_UP");
            default:
                break;
        }
        System.out.println("super.onTouchEvent()默认返回false 表示未消费事件");
        System.out.println("--------------------------------------------------");
        return super.onTouchEvent(event);
    }

    @Override
    public void onUserInteraction() {
        System.out.println(">>> UIResponseActivity 中调用 onUserInteraction()");
        System.out.println("--------------------------------------------------");
        super.onUserInteraction();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, UIResponseActivity.class);
        activity.startActivity(intent);
    }
}
