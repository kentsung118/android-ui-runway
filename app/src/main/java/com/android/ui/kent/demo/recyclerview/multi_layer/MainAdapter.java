package com.android.ui.kent.demo.recyclerview.multi_layer;

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
import com.android.ui.kent.demo.common.decoration.SpacesItemDecoration;
import com.android.ui.kent.demo.recyclerview.multi_layer.lookback.DateAdapter;
import com.android.ui.kent.demo.recyclerview.multi_layer.lookback.LookBackAdapter;
import com.android.ui.kent.demo.recyclerview.multi_layer.lookback.LookBackVO;
import com.android.ui.kent.demo.recyclerview.multi_layer.lookback.TextAdapter;
import com.android.ui.kent.demo.recyclerview.multi_layer.model.MainVO;
import com.android.ui.kent.demo.recyclerview.multi_layer.model.TextVO;
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
        return position == 1 ? ITEM_TYPE_1.ordinal() : ITEM_TYPE_2.ordinal();
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
        @BindView(R.id.content)
        LinearLayout mContent;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.lookbackRv)
        FocusableQuickRecyclerView mContentRv;
        @BindView(R.id.lookback_date_Rv)
        FocusableQuickRecyclerView mDateRv;

        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind() {
            int position = getAdapterPosition();
            mTitle.setText(dataList.get(position).getTitle());


//            mContentRv.addOnFocusChangedListener(new FocusableQuickRecyclerView.FocusChangedListener() {
//                @Override
//                public void onFocusChanged(boolean gainFocus, int direction) {
//                    if (gainFocus) {
//                        if (direction == View.FOCUS_DOWN) {
//                            if (mOnItemFocusedListener != null) {
//                                mOnItemFocusedListener.onFocused(position, position, itemRoot);
//                            }
//                        }
//                        mTitle.setTextColor(mContext.getResources().getColor(R.color.color_f1f1f1));
//                    } else {
//                        if (direction == View.FOCUS_UP) {
//                            if (mOnItemFocusedListener != null) {
//                                mOnItemFocusedListener.onLoseFocus(position, position, itemRoot);
//                            }
//                            mTitle.setTextColor(mContext.getResources().getColor(R.color.liveChannelTextColor60));
//                        }
//                    }
//                }
//            });

            mContentRv.setFocusGainListener((chld, focued) -> {
                if (mOnItemFocusedListener != null) {
                    mOnItemFocusedListener.onFocused(position, position, itemRoot);
                }
                mTitle.setTextColor(mContext.getResources().getColor(R.color.color_f1f1f1));
                open(mContent);
            });

            mContentRv.setFocusLostListener((lastFocusChild, direction) -> {
                if (direction == View.FOCUS_UP) {
                    if (mOnItemFocusedListener != null) {
                        mOnItemFocusedListener.onLoseFocus(position, position, itemRoot);
                        close(mContent);
                    }
                    mTitle.setTextColor(mContext.getResources().getColor(R.color.liveChannelTextColor60));
                }
            });

//            mDateRv.addOnFocusChangedListener(new FocusableQuickRecyclerView.FocusChangedListener(){
//                @Override
//                public void onFocusChanged(boolean gainFocus, int direction) {
//                    if(gainFocus){
//                        if(direction == View.FOCUS_UP){
//                            if (mOnItemFocusedListener != null) {
//                                mOnItemFocusedListener.onFocused(position, position, itemRoot);
//                            }
//                        }
//                        mTitle.setTextColor(mContext.getResources().getColor(R.color.color_f1f1f1));
//                    } else {
//                        if (direction == View.FOCUS_DOWN) {
//                            if (mOnItemFocusedListener != null) {
//                                mOnItemFocusedListener.onLoseFocus(position, position, itemRoot);
//                            }
//                            mTitle.setTextColor(mContext.getResources().getColor(R.color.liveChannelTextColor60));
//                        }
//                    }
//                }
//            });

            mDateRv.setFocusGainListener(new FocusableQuickRecyclerView.FocusGainListener() {
                @Override
                public void onFocusGain(View chld, View focued) {
                    if (mOnItemFocusedListener != null) {
                        mOnItemFocusedListener.onFocused(position, position, itemRoot);
                    }
                    mTitle.setTextColor(mContext.getResources().getColor(R.color.color_f1f1f1));
                    open(mContent);
                }
            });

            mDateRv.setFocusLostListener(new FocusableQuickRecyclerView.FocusLostListener() {
                @Override
                public void onFocusLost(View lastFocusChild, int direction) {
                    if (direction == View.FOCUS_DOWN) {
                        if (mOnItemFocusedListener != null) {
                            mOnItemFocusedListener.onLoseFocus(position, position, itemRoot);
                            close(mContent);

                        }
                        mTitle.setTextColor(mContext.getResources().getColor(R.color.liveChannelTextColor60));
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
            list.add(new LookBackVO("12:00", "", ""));
            list.add(new LookBackVO("12:00", "", ""));
            list.add(new LookBackVO("12:00", "", ""));


            mContentRv.setLayoutManager(new CenterLayoutManger(mContentRv.getContext(), RecyclerView.HORIZONTAL, false));
            mContentRv.addItemDecoration(new SpacesItemDecoration(10, 10, 10));

            LookBackAdapter adapter = new LookBackAdapter(list);
            adapter.bindToRecyclerView(mContentRv);

            List<TextVO> textList = new ArrayList<>();
            textList.add(new TextVO("8月18日"));
            textList.add(new TextVO("8月19日"));
            textList.add(new TextVO("8月20日"));
            textList.add(new TextVO("8月21日"));
            textList.add(new TextVO("8月22日"));
            textList.add(new TextVO("8月23日"));
            textList.add(new TextVO("今天"));

            mDateRv.setLayoutManager(new CenterLayoutManger(mContentRv.getContext(), RecyclerView.HORIZONTAL, false));
            mDateRv.addItemDecoration(new SpacesItemDecoration(10, 10, 40));

            DateAdapter textAdapter = new DateAdapter(textList);
            textAdapter.bindToRecyclerView(mDateRv);

            mContentRv.setGainFocusChangeDescendant(true);
            mContentRv.setGainFocusChangeDescendant(true);
        }

    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.itemRoot)
        LinearLayout itemRoot;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.content)
        LinearLayout mContent;
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
            rv.setFocusGainListener(new FocusableQuickRecyclerView.FocusGainListener() {
                @Override
                public void onFocusGain(View chld, View focued) {
                    if (mOnItemFocusedListener != null) {
                        mOnItemFocusedListener.onFocused(position, position, itemRoot);
                    }
                    mTitle.setTextColor(mContext.getResources().getColor(R.color.color_f1f1f1));
                    open(mContent);
                }
            });

            rv.setFocusLostListener(new FocusableQuickRecyclerView.FocusLostListener() {
                @Override
                public void onFocusLost(View lastFocusChild, int direction) {
                    if (mOnItemFocusedListener != null) {
                        mOnItemFocusedListener.onLoseFocus(position, position, itemRoot);
                    }
                    mTitle.setTextColor(mContext.getResources().getColor(R.color.liveChannelTextColor60));
                    close(mContent);
                }
            });

            List<TextVO> list = new ArrayList<>();
            list.add(new TextVO("480P"));
            list.add(new TextVO("720P"));
            list.add(new TextVO("1080P"));
            list.add(new TextVO("2160P"));

            rv.setLayoutManager(new CenterLayoutManger(rv.getContext(), RecyclerView.HORIZONTAL, false));
            rv.addItemDecoration(new SpacesItemDecoration(10, 10, 10));
            TextAdapter adapter = new TextAdapter(list);
            adapter.bindToRecyclerView(rv);
            rv.setCanFocusOutHorizontal(false);
            rv.setCanFocusOutVertical(true);
            rv.setDebugMode(true);
            rv.setGainFocusChangeDescendant(true);
        }

    }

    public interface OnItemFocusedListener {
        void onFocused(int position, int realPosition, View view);

        void onLoseFocus(int position, int realPosition, View view);
    }

    public void setmOnItemFocusedListener(OnItemFocusedListener mOnItemFocusedListener) {
        this.mOnItemFocusedListener = mOnItemFocusedListener;
    }


    public void open(LinearLayout rootItemView) {
        ViewWrapper vw = new ViewWrapper(rootItemView);
        LinearLayout content = rootItemView.findViewById(R.id.content);
        TextView title = rootItemView.findViewById(R.id.title);

        ObjectAnimator oa = ObjectAnimator.ofInt(vw, "height", 0, 230);
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(content, "alpha", 0, 1);
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(title, "scaleY", 0.7f, 1.0f);
        ObjectAnimator oa3 = ObjectAnimator.ofFloat(title, "scaleX", 0.7f, 1.0f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(oa, oa1, oa2, oa3);
        set.setDuration(0);
//        set.start();
//        set.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation, boolean isReverse) {
////                super.onAnimationStart(animation);
//                rv.setVisibility(View.VISIBLE);
////                mContentRv.getAdapter().notifyDataSetChanged();
//            }
//        });
    }

    public void close(LinearLayout rootItemView) {
        ViewWrapper vw = new ViewWrapper(rootItemView);
        LinearLayout content = rootItemView.findViewById(R.id.content);
        TextView title = rootItemView.findViewById(R.id.title);

        ObjectAnimator oa = ObjectAnimator.ofInt(vw, "height", 230, 0);
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(content, "alpha", 1, 0);
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(title, "scaleY", 1.0f, 0.7f);
        ObjectAnimator oa3 = ObjectAnimator.ofFloat(title, "scaleX", 1.0f, 0.7f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(oa, oa1, oa2, oa3);
        set.playTogether(oa);
        set.setDuration(0);
//        set.start();
//        set.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
////                super.onAnimationEnd(animation);
//                rv.setVisibility(View.INVISIBLE);
//            }
//        });
    }

}
