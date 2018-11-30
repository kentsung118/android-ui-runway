package com.android.ui.kent.demo.recyclerview.multi_layer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.recyclerview.multi_layer.model.MainVO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.ui.kent.demo.recyclerview.ItemAdapter.VIEW_TYPE.ITEM_TYPE_1;

/**
 * Created by Kent Song on 2018/11/30.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public enum VIEW_TYPE {
        ITEM_TYPE_1,
        ITEM_TYPE_2
    }

    private Context mContext;
    private List<MainVO> dataList;
    protected OnItemFocusedListener mOnItemFocusedListener;


//    public ItemAdapter.ViewHolderClickListener mListener;

    public MainAdapter(Context mContext, List<MainVO> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        //ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //依viewType決定使用item_layout
        View rootView;
        if (viewType == ITEM_TYPE_1.ordinal()) {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_multi_rv_main_item, null, false);
            //rootView.setLayoutParams(lp);
            return new MainAdapter.ViewHolder1(rootView);
        }
        return null;
//        else{
//            rootView = LayoutInflater.from(mContext).inflate(R.layout.view_item_recycler_type_2, null, false);
//            //rootView.setLayoutParams(lp);
//            return new MainAdapter.ViewHolder2(rootView);
//        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        //可以彈性Bind兩種以上viewHolder
        if (viewHolder instanceof MainAdapter.ViewHolder1) {
            MainAdapter.ViewHolder1 holder = (MainAdapter.ViewHolder1) viewHolder;
            holder.mTitle.setText(dataList.get(position).getTitle());

            holder.itemRoot.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (mOnItemFocusedListener != null) {
                            mOnItemFocusedListener.onFocused(position, position, holder.itemRoot);
                        }
                        holder.mTitle.setTextColor(mContext.getResources().getColor(R.color.liveChannelTextColor));
//                    carouselHolder.open();

                    } else {
                        if (mOnItemFocusedListener != null) {
                            mOnItemFocusedListener.onUnFocused(position, position, holder.itemRoot);
                        }
                        holder.mTitle.setTextColor(mContext.getResources().getColor(R.color.liveChannelTextColor60));
                    }

                }
            });


//            holder.subText.setText(dataList.get(position).content);
        }
// else{
//            MainAdapter.ViewHolder2 holder = (MainAdapter.ViewHolder2)viewHolder;
//            holder.mTitle.setText(dataList.get(position).title);
//            holder.subText.setText(dataList.get(position).content);
//        }

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_1.ordinal();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.itemRoot)
        LinearLayout itemRoot;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.content)
        LinearLayout content;
        @BindView(R.id.lookbackRv)
        RecyclerView rv;


        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

//    class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        @BindView(R.id.text_1)
//        TextView mTitle;
//        @BindView(R.id.text_2)
//        TextView subText;
//
//        public ViewHolder2(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            mListener.onClick(v, getLayoutPosition());
//        }
//
//    }

    public interface OnItemFocusedListener {
        void onFocused(int position, int realPosition, View view);
        void onUnFocused(int position, int realPosition, View view);
    }

    public void setmOnItemFocusedListener(OnItemFocusedListener mOnItemFocusedListener) {
        this.mOnItemFocusedListener = mOnItemFocusedListener;
    }
}
