package com.android.ui.kent.demo.recyclerview.multi_layer.lookback;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.recyclerview.multi_layer.model.TextVO;
import com.android.ui.kent.demo.recyclerview.util.FocusQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Kent Song on 2018/12/1.
 */
public class TextAdapter extends FocusQuickAdapter<TextVO, BaseViewHolder> {

    public TextAdapter(@Nullable List<TextVO> data) {
        super(R.layout.layout_multi_layer_item_text, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextVO item) {
        super.convert(helper, item);
        ConstraintLayout rootView = helper.getView(R.id.item_cl);
        TextView itemTv = helper.getView(R.id.item_tv);
        itemTv.setText(item.getText());
        Timber.d(">> itemTv = %s", itemTv);
        rootView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rootView.setBackgroundColor(rootView.getResources().getColor(R.color.main_dark_blue));
                    itemTv.setTextColor(rootView.getResources().getColor(R.color.text_white));
                } else {
                    rootView.setBackgroundColor(rootView.getResources().getColor(R.color.color_33d8d8d8));
                    itemTv.setTextColor(rootView.getResources().getColor(R.color.color_F1F1F1));
                }
            }
        });

    }
}
