package com.android.ui.kent.demo.ui_response.touch_move;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.ui.kent.R;
import com.android.ui.kent.demo.recyclerview.util.FocusQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

/**
 * Created by Kent Song on 2018/12/1.
 */
public class ListAdapter extends FocusQuickAdapter<ListAdapter.ButtonVO, BaseViewHolder> {

    public ListAdapter(@Nullable List<ButtonVO> data) {
        super(R.layout.layout_ui_touch_move_item, data);
    }

    @Override protected void convert(BaseViewHolder helper, ButtonVO item) {
        super.convert(helper, item);
        Button itemTv = helper.getView(R.id.item_tv);
        itemTv.setText(item.getText());
    }

    public static class ButtonVO {

        private String text;

        public ButtonVO(String title) {
            this.text = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Log.d("kentsung", "onBindViewHolder position="+position);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("kentsung", "onCreateViewHolder viewType="+viewType);

        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        Log.d("kentsung", "onViewAttachedToWindow holder="+holder);

        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        Log.d("kentsung", "onViewDetachedFromWindow holder="+holder);

        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        Log.d("kentsung", "onViewRecycled holder="+holder+", pos="+holder.getLayoutPosition());
        super.onViewRecycled(holder);
    }
}
