package com.android.ui.kent.demo.ui_response.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Kent on 2018/4/27.
 */

public class MyLinerLayout extends LinearLayout {

    public MyLinerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //super.dispatchTouchEvent(ev);
        System.out.println("内层MyLinearLayout 中调用  dispatchTouchEvent()");
        System.out.println("super.dispatchTouchEvent()默认返回true 表示继续分发");
        System.out.println("--------------------------------------------------");
        return super.dispatchTouchEvent(ev);
        //return false;
    }

    //覆写自ViewGroup
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //super.onInterceptTouchEvent(ev);
        System.out.println("内层MyLinearLayout 中调用  onInterceptTouchEvent()");
        System.out.println("super.onInterceptTouchEvent()默认返回false 表示不拦截");
        System.out.println("--------------------------------------------------");
        return super.onInterceptTouchEvent(ev);

    }
    //注意:
    //1 ViewGroup是View的子类
    //2 ViewGroup中onTouchEvent()方法默认返回的是false
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("内层MyLinearLayout 中调用  onTouchEvent()--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("内层MyLinearLayout 中调用  onTouchEvent()--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("内层MyLinearLayout 中调用  onTouchEvent()--->ACTION_UP");
            default:
                break;
        }
        System.out.println("super.onTouchEvent()默认返回false 表示未消费事件");
        System.out.println("--------------------------------------------------");
        return super.onTouchEvent(event);
    }
}
