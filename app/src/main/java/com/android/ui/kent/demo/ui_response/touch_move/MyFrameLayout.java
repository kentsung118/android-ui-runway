package com.android.ui.kent.demo.ui_response.touch_move;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import timber.log.Timber;

public class MyFrameLayout extends HorizontalScrollView {

    public MyFrameLayout(@NonNull Context context) {
        super(context);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isIntercept;
    private boolean isSolve;//是否完成了拦截判断，如果决定拦截，那么同系列事件就不能设置为不拦截
    private PointGapF mPointGapF = new PointGapF();

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Timber.d("ACTION_DOWN");
                mPointGapF.x = ev.getX();
                mPointGapF.y = ev.getY();
                return false;//down的时候拦截后，就只能交给自己处理了

            case MotionEvent.ACTION_MOVE:
                Timber.d("ACTION_MOVE");
                //if (!isSolve) {//是否已经决定拦截/不拦截？
                    isIntercept = (Math.abs(ev.getX() - mPointGapF.x)
                            > Math.abs(ev.getY() - mPointGapF.y) * 2);//如果是左右滑动，且水平角度小于30°，就拦截
                    Timber.d("是否攔截:%s",isIntercept);
                    //isSolve = true;
                //}
                return isIntercept;//如果是左右滑动，就拦截
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Timber.d("移動:%s",mPointGapF.x - ev.getX());
                scrollBy((int) (mPointGapF.x - ev.getX()), 0);

                mPointGapF.x = ev.getX();
                mPointGapF.y = ev.getY();
                break;
        }
        return super.onTouchEvent(ev);
    }

    public class PointGapF {
        float x, y;
    }
}
