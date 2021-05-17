package com.android.ui.kent.demo.ui_response.touch_event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Created by Kent on 2018/4/27.
 */

public class MyConstrainLayout extends ConstraintLayout {

    public MyConstrainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //super.dispatchTouchEvent(ev);  
        Log.i(UIResponseActivity.TAG,"外层MyConstrainLayout 中调用  dispatchTouchEvent()");
        Log.i(UIResponseActivity.TAG,"super.dispatchTouchEvent()默认返回true 表示继续分发");
        Log.i(UIResponseActivity.TAG,"--------------------------------------------------");
        return super.dispatchTouchEvent(ev);
        //return false;  
    }

    //覆写自ViewGroup  
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(UIResponseActivity.TAG,"外层MyConstrainLayout 中调用  onInterceptTouchEvent()");
        Log.i(UIResponseActivity.TAG,"super.onInterceptTouchEvent()默认返回false 表示不拦截");
        Log.i(UIResponseActivity.TAG,"--------------------------------------------------");
        return super.onInterceptTouchEvent(ev);
        //return true;
    }

    //注意:  
    //1 ViewGroup是View的子类  
    //2 ViewGroup中onTouchEvent()方法默认返回的是false  
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //super.onTouchEvent(event);  
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(UIResponseActivity.TAG,"外层MyConstrainLayout 中调用  onTouchEvent()--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(UIResponseActivity.TAG,"外层MyConstrainLayout 中调用  onTouchEvent()--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(UIResponseActivity.TAG,"外层MyConstrainLayout 中调用  onTouchEvent()--->ACTION_UP");
            default:
                break;
        }
        Log.i(UIResponseActivity.TAG,"super.onTouchEvent()默认返回false 表示未消费事件");
        Log.i(UIResponseActivity.TAG,"--------------------------------------------------");
        return super.onTouchEvent(event);
        //return true;  
    }

}
