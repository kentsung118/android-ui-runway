package com.android.ui.kent.demo.recyclerview.multi_layer.lookback;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.recyclerview.util.FocusQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import timber.log.Timber;

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

        ConstraintLayout rootView = helper.getView(R.id.item_lookback_cl);
        rootView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    rootView.setBackgroundColor(rootView.getResources().getColor(R.color.main_dark_blue));
                } else {
                    rootView.setBackgroundColor(rootView.getResources().getColor(R.color.color_33d8d8d8));
                }
            }
        });

    }
}
