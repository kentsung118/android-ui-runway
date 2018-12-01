package com.android.ui.kent.demo.recyclerview.multi_layer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.recyclerview.multi_layer.lookback.LookBackAdapter;
import com.android.ui.kent.demo.recyclerview.multi_layer.lookback.LookBackVO;
import com.android.ui.kent.demo.recyclerview.multi_layer.model.MainVO;
import com.android.ui.kent.demo.recyclerview.multi_layer.setting.SettingAdapter;
import com.android.ui.kent.demo.recyclerview.multi_layer.setting.SettingVO;
import com.android.ui.kent.demo.recyclerview.util.FocusableQuickRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.android.ui.kent.demo.recyclerview.ItemAdapter.VIEW_TYPE.ITEM_TYPE_1;
import static com.android.ui.kent.demo.recyclerview.ItemAdapter.VIEW_TYPE.ITEM_TYPE_2;

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

    public MainAdapter(Context mContext, List<MainVO> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        return position < 2 ? ITEM_TYPE_1.ordinal() : ITEM_TYPE_2.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
//        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //依viewType決定使用item_layout
        View rootView;
        if (viewType == ITEM_TYPE_1.ordinal()) {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_multi_rv_main_lookback_item, null, false);
//            rootView.setLayoutParams(lp);
            return new MainAdapter.ViewHolder1(rootView);
        } else {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_multi_rv_main_setting_item, null, false);
//            rootView.setLayoutParams(lp);
            return new MainAdapter.ViewHolder2(rootView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        //可以彈性Bind兩種以上viewHolder
        if (viewHolder instanceof MainAdapter.ViewHolder1) {
            MainAdapter.ViewHolder1 holder = (MainAdapter.ViewHolder1) viewHolder;
            holder.bind();
        } else if (viewHolder instanceof MainAdapter.ViewHolder2) {
            MainAdapter.ViewHolder2 holder = (MainAdapter.ViewHolder2) viewHolder;
            holder.bind();
        }

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.itemRoot)
        LinearLayout itemRoot;
        @BindView(R.id.title)
        TextView mTitle;
        //        @BindView(R.id.content)
//        LinearLayout content;
        @BindView(R.id.lookbackRv)
        FocusableQuickRecyclerView rv;

        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind() {
            int position = getAdapterPosition();
            mTitle.setText(dataList.get(position).getTitle());

            itemRoot.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (mOnItemFocusedListener != null) {
                            mOnItemFocusedListener.onFocused(position, position, itemRoot);
                        }
                        mTitle.setTextColor(mContext.getResources().getColor(R.color.color_F1F1F1));
                        open(rv);
                        rv.requestFocus();
                    } else {
                        if (mOnItemFocusedListener != null) {
                            mOnItemFocusedListener.onUnFocused(position, position, itemRoot);
                        }
                        mTitle.setTextColor(mContext.getResources().getColor(R.color.liveChannelTextColor60));
                        close(rv);
                    }

                }
            });


            List<LookBackVO> list = new ArrayList<>();
            list.add(new LookBackVO("12:00", "", ""));
            list.add(new LookBackVO("12:00", "", ""));
            list.add(new LookBackVO("12:00", "", ""));
            list.add(new LookBackVO("12:00", "", ""));
            list.add(new LookBackVO("12:00", "", ""));
            list.add(new LookBackVO("12:00", "", ""));
            list.add(new LookBackVO("12:00", "", ""));
            list.add(new LookBackVO("12:00", "", ""));
            list.add(new LookBackVO("12:00", "", ""));
            list.add(new LookBackVO("12:00", "", ""));


            rv.setLayoutManager(new CenterLayoutManger(rv.getContext(), RecyclerView.HORIZONTAL, false));
            LookBackAdapter adapter = new LookBackAdapter(list);
            adapter.bindToRecyclerView(rv);
        }


    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.itemRoot)
        LinearLayout itemRoot;
        @BindView(R.id.title)
        TextView mTitle;
        //        @BindView(R.id.content)
//        LinearLayout content;
        @BindView(R.id.text_rv)
        FocusableQuickRecyclerView rv;

        public ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind() {
            int position = getAdapterPosition();
            Timber.d(">> position = %s, onBind", position);

            mTitle.setText(dataList.get(position).getTitle());

            itemRoot.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (mOnItemFocusedListener != null) {
                            mOnItemFocusedListener.onFocused(position, position, itemRoot);
                        }
                        mTitle.setTextColor(mContext.getResources().getColor(R.color.color_F1F1F1));
                        Timber.d(">> position = %s is open", position);
                        open(rv);
                        rv.requestFocus();
//                        itemRoot.setFocusable(false);

                    } else {
                        if (mOnItemFocusedListener != null) {
                            mOnItemFocusedListener.onUnFocused(position, position, itemRoot);
                        }
                        mTitle.setTextColor(mContext.getResources().getColor(R.color.liveChannelTextColor60));
                        Timber.d(">> position = %s is close", position);
                        close(rv);
                    }
                }
            });

            List<SettingVO> list = new ArrayList<>();
            list.add(new SettingVO("720P"));
            list.add(new SettingVO("720P"));
            list.add(new SettingVO("720P"));
            list.add(new SettingVO("720P"));
            list.add(new SettingVO("720P"));
            list.add(new SettingVO("720P"));
            list.add(new SettingVO("720P"));

            rv.setLayoutManager(new CenterLayoutManger(rv.getContext(), RecyclerView.HORIZONTAL, false));
            SettingAdapter adapter = new SettingAdapter(list);
            adapter.bindToRecyclerView(rv);
            rv.setCanFocusOutHorizontal(false);
            rv.setCanFocusOutVertical(true);
            rv.setFocusLostListener(new FocusableQuickRecyclerView.FocusLostListener() {
                @Override
                public void onFocusLost(View lastFocusChild, int direction) {
                    if(direction == View.FOCUS_DOWN || direction == View.FOCUS_UP){
                        Timber.d(">> ViewHolder2 rv onFocusLost");
//                        itemRoot.setFocusable(true);
                    }
                }
            });
            rv.setDebugMode(true);
        }

    }

    public interface OnItemFocusedListener {
        void onFocused(int position, int realPosition, View view);

        void onUnFocused(int position, int realPosition, View view);
    }

    public void setmOnItemFocusedListener(OnItemFocusedListener mOnItemFocusedListener) {
        this.mOnItemFocusedListener = mOnItemFocusedListener;
    }


    public void open(FocusableQuickRecyclerView rv) {
        ViewWrapper vw = new ViewWrapper(rv);
        //ObjectAnimator oa = ObjectAnimator.ofFloat(itemRoot, "scaleY", 0.5f, 1.0f);
        ObjectAnimator oa = ObjectAnimator.ofInt(vw, "height", 0, 150);
//        ObjectAnimator oa1 = ObjectAnimator.ofFloat(content, "alpha", 0, 1);
//        ObjectAnimator oa2 = ObjectAnimator.ofFloat(title, "scaleY", 0.7f, 1.0f);
//        ObjectAnimator oa3 = ObjectAnimator.ofFloat(title, "scaleX", 0.7f, 1.0f);
        AnimatorSet set = new AnimatorSet();
//        set.playTogether(oa, oa1, oa2, oa3);
        set.playTogether(oa);
        set.setDuration(500);
//        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {
//                super.onAnimationStart(animation);
                rv.setVisibility(View.VISIBLE);
//                rv.getAdapter().notifyDataSetChanged();
            }
        });
    }

    public void close(FocusableQuickRecyclerView rv) {
        ViewWrapper vw = new ViewWrapper(rv);
        // ObjectAnimator oa = ObjectAnimator.ofFloat(itemRoot, "scaleY", 1.0f, 0.5f);
        ObjectAnimator oa = ObjectAnimator.ofInt(vw, "height", 150, 0);
//        ObjectAnimator oa1 = ObjectAnimator.ofFloat(content, "alpha", 1, 0);
//        ObjectAnimator oa2 = ObjectAnimator.ofFloat(title, "scaleY", 1.0f, 0.7f);
//        ObjectAnimator oa3 = ObjectAnimator.ofFloat(title, "scaleX", 1.0f, 0.7f);
        AnimatorSet set = new AnimatorSet();
//        set.playTogether(oa, oa1, oa2, oa3);
        set.playTogether(oa);
        set.setDuration(500);
//        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
                rv.setVisibility(View.INVISIBLE);
            }
        });
    }

}
