package com.android.ui.kent.demo.recyclerview.scroll;

import android.support.annotation.Nullable;
import android.widget.Button;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.recyclerview.multi_layer.model.TextVO;
import com.android.ui.kent.demo.recyclerview.util.FocusableAdapter;
import com.android.ui.kent.demo.recyclerview.util.SimpleData;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Kent Song on 2018/12/1.
 */
public class Text2Adapter extends FocusableAdapter<TextVO> {

    public Text2Adapter(@Nullable List<SimpleData<TextVO>> data) {
        super(R.layout.layout_multi_layer_item_text, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SimpleData<TextVO> item) {

        Button itemTv = helper.getView(R.id.item_tv);
        itemTv.setText(item.getData().getText());
        if (item.isFocused()) {
            Timber.d(">> itemTv onFocus pos = %s", helper.getAdapterPosition());
            itemTv.setBackgroundResource(R.drawable.common_bg_focus);
            itemTv.setTextColor(itemTv.getResources().getColor(R.color.text_white));
        } else {
            itemTv.setBackgroundResource(R.drawable.common_bg_focus_border_normal);
            itemTv.setTextColor(itemTv.getResources().getColor(R.color.color_f1f1f1));
        }


//        Timber.d(">> itemTv = %s", itemTv);
//        itemTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    Timber.d(">> itemTv onFocus pos = %s", helper.getAdapterPosition());
//                    itemTv.setBackgroundResource(R.drawable.common_bg_focus);
//                    itemTv.setTextColor(itemTv.getResources().getColor(R.color.text_white));
//                } else {
//                    itemTv.setBackgroundResource(R.drawable.common_bg_focus_border_normal);
//                    itemTv.setTextColor(itemTv.getResources().getColor(R.color.color_f1f1f1));
//                }
//            }
//        });


    }

//        @Override
//    protected void convert(BaseViewHolder helper, TextVO item) {
//        super.convert(helper, item);
////        ConstraintLayout rootView = helper.getView(R.id.item_cl);
//        Button itemTv = helper.getView(R.id.item_tv);
//        itemTv.setText(item.getText());
////        Timber.d(">> itemTv = %s", itemTv);
//        itemTv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    Timber.d(">> itemTv onFocus pos = %s", helper.getAdapterPosition());
//                    itemTv.setBackgroundResource(R.drawable.common_bg_focus);
//                    itemTv.setTextColor(itemTv.getResources().getColor(R.color.text_white));
//                } else {
//                    itemTv.setBackgroundResource(R.drawable.common_bg_focus_border_normal);
//                    itemTv.setTextColor(itemTv.getResources().getColor(R.color.color_f1f1f1));
//                }
//            }
//        });
//
//    }
}
