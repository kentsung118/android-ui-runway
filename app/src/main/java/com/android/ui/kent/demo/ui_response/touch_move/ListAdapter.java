package com.android.ui.kent.demo.ui_response.touch_move;

import android.support.annotation.Nullable;
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
}
