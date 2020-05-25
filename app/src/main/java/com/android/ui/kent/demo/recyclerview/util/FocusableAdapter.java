package com.android.ui.kent.demo.recyclerview.util;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by KentSong on 2018/6/11.
 * <p>
 * FocusableAdapter 适配器
 * <p>
 * 使用 SimpleData 通用物件封装资料
 *
 * @See {@link SimpleData}
 */
public abstract class FocusableAdapter<D> extends BaseQuickAdapter<SimpleData<D>, BaseViewHolder> {

    protected int mLastFocusIndex;

    public FocusableAdapter(@Nullable List<SimpleData<D>> data) {
        super(data);
    }

    public FocusableAdapter(int layoutResId, @Nullable List<SimpleData<D>> data) {
        super(layoutResId, data);
    }

    public void setItemFocused(int index) {
        SimpleData ori = mData.get(mLastFocusIndex);
        ori.setFocused(false);
        ori.setSelected(false);
        notifyItemChanged(mLastFocusIndex);

        SimpleData target = mData.get(index);
        target.setFocused(true);
        target.setSelected(true);
        mLastFocusIndex = index;

        notifyItemChanged(index);
    }

    public void setItemUnFocus(int index) {
        SimpleData data = mData.get(index);
        data.setFocused(false);

        notifyItemChanged(index);
    }

    public void setItemUnSelect(int index) {
        SimpleData data = mData.get(index);
        data.setSelected(false);

        notifyItemChanged(index);
    }

    public int getDataItemCount(){
        return mData.size();
    }

    public void setLastFocusIndex(int lastFocusIndex) {
        this.mLastFocusIndex = lastFocusIndex;
    }

}
