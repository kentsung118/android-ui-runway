package com.android.ui.kent.demo.recyclerview.multi_layer;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

public class CenterLayoutManger extends LinearLayoutManager {

    private static final float DEFAULT_SPEED_PERPIXEL = 200f;

    private float mSpeedPerPixel;

    private int mPixel;

    public CenterLayoutManger(Context context) {
        this(context, VERTICAL, false, DEFAULT_SPEED_PERPIXEL);
    }

    public CenterLayoutManger(Context context, float speedPerPixel) {
        this(context, VERTICAL, false, speedPerPixel);
    }

    public CenterLayoutManger(Context context, int orientation, boolean reverseLayout) {
        this(context, orientation, reverseLayout, DEFAULT_SPEED_PERPIXEL);
    }

    public CenterLayoutManger(Context context, int orientation, boolean reverseLayout, int pix) {
        this(context, orientation, reverseLayout, DEFAULT_SPEED_PERPIXEL);
        mPixel = pix;
    }

    public CenterLayoutManger(Context context, int orientation, boolean reverseLayout, float speedPerPixel) {
        super(context, orientation, reverseLayout);
        this.mSpeedPerPixel = speedPerPixel;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private class CenterSmoothScroller extends LinearSmoothScroller {

        CenterSmoothScroller(Context context) {
            super(context);
        }

        @Nullable
        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return CenterLayoutManger.this.computeScrollVectorForPosition(targetPosition);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2) + mPixel;
        }

        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return mSpeedPerPixel / displayMetrics.densityDpi;
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }
    }


}