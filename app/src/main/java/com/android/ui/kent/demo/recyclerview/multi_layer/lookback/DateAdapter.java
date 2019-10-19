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
public class DateAdapter extends FocusQuickAdapter<TextVO, BaseViewHolder> {

    public DateAdapter(@Nullable List<TextVO> data) {
        super(R.layout.layout_multi_layer_item_date, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextVO item) {
        super.convert(helper, item);
        TextView dateTv = helper.getView(R.id.date_tv);
        dateTv.setText(item.getText());
        Timber.d(">> itemTv = %s", dateTv);
        dateTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dateTv.setBackgroundColor(dateTv.getResources().getColor(R.color.main_dark_blue));
                    dateTv.setTextColor(dateTv.getResources().getColor(R.color.color_ffffff));
                } else {
                    dateTv.setBackgroundColor(0);
                    dateTv.setTextColor(dateTv.getResources().getColor(R.color.color_f1f1f1));
                }
            }
        });

    }
}
