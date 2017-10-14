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

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<UserVO> dataList;

    public UserAdapter(Context context, List<UserVO> list) {
        this.mContext = context;
        this.dataList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //依viewType決定使用item_layout
        View rootView;
        rootView =  LayoutInflater.from(mContext).inflate(R.layout.view_item_recycler_user, null, false);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);


        //if(viewType == ITEM_TYPE_1.ordinal()){
        //    rootView =  LayoutInflater.from(mContext).inflate(R.layout.view_item_recycler_type_1, null, false);
        //    rootView.setLayoutParams(lp);
        //    return new ViewHolder1(rootView);
        //}
        //else{
        //    rootView = LayoutInflater.from(mContext).inflate(R.layout.view_item_recycler_type_2, null, false);
        //    rootView.setLayoutParams(lp);
        //    return new ViewHolder2(rootView);
        //}
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserVO userVO = dataList.get(position);

        //可以彈性Bind兩種以上viewHolder
        if(holder instanceof ViewHolder){
            ViewHolder view = (ViewHolder)holder;
            view.userId.setText(userVO.userId);
            view.userPwd.setText(userVO.userPwd);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
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
}
