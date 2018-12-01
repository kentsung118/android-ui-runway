package com.android.ui.kent.demo.recyclerview.multi_layer.lookback;

import android.support.annotation.Nullable;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.recyclerview.util.FocusQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Kent Song on 2018/12/1.
 */
public class LookBackAdapter extends FocusQuickAdapter<LookBackVO, BaseViewHolder> {

    public LookBackAdapter(@Nullable List<LookBackVO> data) {
        super(R.layout.layout_multi_layer_item_lookback, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LookBackVO item) {
        super.convert(helper, item);

//        helper.getView()


    }
}
