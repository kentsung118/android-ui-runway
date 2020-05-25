package com.android.ui.kent.demo.common.decoration;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * 频道view 间距
 * Created by andyliu on 2018/4/2.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {


    /**
     * 第一个Item左侧间距
     */
    private int mFirstHeadSpace;
    /**
     * 顶部间隔
     */
    private int mTopSpace;
    /**
     * 右侧间隔
     */
    private int mRightSpace;

    public SpacesItemDecoration(int mFirstLeftSpace, int mTopSpace, int mRightSpace) {
        this.mFirstHeadSpace = mFirstLeftSpace;
        this.mTopSpace = mTopSpace;
        this.mRightSpace = mRightSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.right = mRightSpace;
        if (parent.getChildLayoutPosition(view) == 0) {
            if (mFirstHeadSpace > 0) {
                outRect.left = mFirstHeadSpace;
            }
        }
        if (mTopSpace > 0) {
            outRect.top = mTopSpace;
        }
    }
}