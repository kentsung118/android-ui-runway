package com.android.ui.kent.demo.ui_response.touch_move;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import timber.log.Timber;

public class CustomView extends ScrollView {

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private PointGapF mPointGapF = new PointGapF();

    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Timber.d("ACTION_DOWN");

                mPointGapF.x = ev.getX();
                mPointGapF.y = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Timber.d("ACTION_MOVE");

                scrollBy(0, (int) (mPointGapF.y - ev.getY()));
                mPointGapF.x = ev.getX();
                mPointGapF.y = ev.getY();
                break;
        }
        return true;
    }

    public class PointGapF {
        float x, y;
    }
}
