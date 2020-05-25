package com.android.ui.kent.demo.recyclerview.multi_layer.lookback;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        LinearLayout itemLayout = helper.getView(R.id.item_layout);
        TextView timeTv = helper.getView(R.id.tv_time);
        TextView contentTv = helper.getView(R.id.tv_content);
        itemLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    timeTv.setTextColor(timeTv.getResources().getColor(R.color.text_white));
                    timeTv.setBackgroundColor(itemLayout.getResources().getColor(R.color.main_dark_blue));
                    contentTv.setTextColor(contentTv.getResources().getColor(R.color.text_white));
                    contentTv.setBackgroundColor(itemLayout.getResources().getColor(R.color.main_dark_blue));
                } else {
                    timeTv.setTextColor(timeTv.getResources().getColor(R.color.color_f1f1f1));
                    timeTv.setBackgroundColor(itemLayout.getResources().getColor(R.color.color_66d8d8d8));
                    contentTv.setTextColor(contentTv.getResources().getColor(R.color.color_f1f1f1));
                    contentTv.setBackgroundColor(itemLayout.getResources().getColor(R.color.color_33d8d8d8));
                }
            }
        });

    }
}
