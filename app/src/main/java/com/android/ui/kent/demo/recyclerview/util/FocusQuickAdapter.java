package com.android.ui.kent.demo.recyclerview.util;

import androidx.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Kent Song on 2018/6/27.
 */
public class FocusQuickAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    private int mDefaultPosition;

    //返回键获焦使用 (Required)
    private int mItemViewIdRes;

    protected OnItemFocusListener mOnItemFocusListener;

    public FocusQuickAdapter(@Nullable List<T> data) {
        super(data);
    }

    public FocusQuickAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(K helper, T item) {

    }

    public void setItemFocused(int index) {
        View itemView = getViewByPosition(index, mItemViewIdRes);
        if (itemView != null) {
            itemView.requestFocus();
        }

    }

    public interface OnItemFocusListener {
        void onItemFocused(View view, int position);
    }

    public int getDefaultPosition() {
        return mDefaultPosition;
    }

    public void setDefaultPosition(int position) {
        this.mDefaultPosition = position;
    }

    public int getItemViewIdRes() {
        return mItemViewIdRes;
    }

    public void setItemViewIdRes(int mItemViewIdRes) {
        this.mItemViewIdRes = mItemViewIdRes;
    }

    public OnItemFocusListener getmOnItemFocusListener() {
        return mOnItemFocusListener;
    }

    public void setOnItemFocusListener(OnItemFocusListener onItemFocusListener) {
        this.mOnItemFocusListener = onItemFocusListener;
    }

    public int getDataItemCount(){
        return mData.size();
    }
}
