package com.android.ui.kent.demo.ui_response.touch_event;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Kent on 2018/4/27.
 */

public class MyButton extends AppCompatButton {

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("自定义Button 中调用 dispatchTouchEvent()");
        System.out.println("super.dispatchTouchEvent默认返回true");
        System.out.println("--------------------------------------------------");
        return super.dispatchTouchEvent(event);
    }

    //注意：
    //在View的子类中onTouchEvent()方法默认返回的是true
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("自定义Button 中调用 onTouchEvent()--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("自定义Button 中调用 onTouchEvent()--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("自定义Button 中调用 onTouchEvent()--->ACTION_UP");
                break;
            default:
                break;
        }
        System.out.println("super.onTouchEvent()默认返回true");
        System.out.println("--------------------------------------------------");
        return super.onTouchEvent(event);
    }

}
