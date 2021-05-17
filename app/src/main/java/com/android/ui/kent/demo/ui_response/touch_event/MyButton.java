package com.android.ui.kent.demo.ui_response.touch_event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

/**
 * Created by Kent on 2018/4/27.
 */

public class MyButton extends AppCompatButton {

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(UIResponseActivity.TAG,"自定义Button 中调用 dispatchTouchEvent()");
        Log.i(UIResponseActivity.TAG,"super.dispatchTouchEvent默认返回true");
        Log.i(UIResponseActivity.TAG,"--------------------------------------------------");
        return super.dispatchTouchEvent(event);
    }

    //注意：
    //在View的子类中onTouchEvent()方法默认返回的是true
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(UIResponseActivity.TAG,"自定义Button 中调用 onTouchEvent()--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(UIResponseActivity.TAG,"自定义Button 中调用 onTouchEvent()--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(UIResponseActivity.TAG,"自定义Button 中调用 onTouchEvent()--->ACTION_UP");
                break;
            default:
                break;
        }
        Log.i(UIResponseActivity.TAG,"super.onTouchEvent()默认返回true");
        Log.i(UIResponseActivity.TAG,"--------------------------------------------------");
        return super.onTouchEvent(event);
    }

}
