package com.android.ui.kent.demo.recyclerview.multi_layer.setting;

import android.support.annotation.Nullable;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.recyclerview.util.FocusQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Kent Song on 2018/12/1.
 */
public class SettingAdapter extends FocusQuickAdapter<SettingVO, BaseViewHolder> {

    public SettingAdapter(@Nullable List<SettingVO> data) {
        super(R.layout.layout_multi_layer_item_setting, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SettingVO item) {
        super.convert(helper, item);

    }
}
