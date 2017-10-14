package com.android.ui.kent.demo.recyclerview.loadmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.ui.kent.R;
import com.android.ui.kent.model.database.vo.UserVO;
import java.util.List;

/**
 * Created by Kent on 2017/10/14.
 */

public class UserAdapter extends HeaderFooterRecyclerViewAdapter {

    private Context mContext;
    private List<UserVO> dataList;

    private int footerItemCount = 0;

    public UserAdapter(Context context, List<UserVO> list) {
        this.mContext = context;
        this.dataList = list;
    }

    @Override
    protected int getHeaderItemCount() {
        return 0;
    }

    @Override
    protected int getFooterItemCount() {
        return footerItemCount;
    }

    @Override
    protected int getContentItemCount() {
        return dataList.size();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderItemViewHolder(ViewGroup parent,
            int headerViewType) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterItemViewHolder(ViewGroup parent,
            int footerViewType) {
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_load_more, parent, false);
        item.setLayoutParams(lp);
        return new FooterViewHolder(item);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent, int contentViewType) {
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //依viewType決定使用item_layout
        View rootView;
        rootView =  LayoutInflater.from(mContext).inflate(R.layout.view_item_recycler_user, null, false);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    protected void onBindHeaderItemViewHolder(RecyclerView.ViewHolder headerViewHolder,
            int position) {

    }

    @Override
    protected void onBindFooterItemViewHolder(RecyclerView.ViewHolder footerViewHolder, int position) {

    }

    @Override
    protected void onBindContentItemViewHolder(RecyclerView.ViewHolder contentViewHolder, int position) {
        UserVO userVO = dataList.get(position);

        //可以彈性Bind兩種以上viewHolder
        if(contentViewHolder instanceof ViewHolder){
            ViewHolder view = (ViewHolder)contentViewHolder;
            view.userId.setText(userVO.userId);
            view.userPwd.setText(userVO.userPwd);
        }
    }

    public void setFooterState(boolean isVisible) {
        if (isVisible) {
            footerItemCount = 1;
        } else {
            footerItemCount = 0;
        }

        notifyDataSetChanged();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_id)
        TextView userId;
        @BindView(R.id.user_pwd)
        TextView userPwd;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
