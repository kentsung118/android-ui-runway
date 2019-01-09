package com.android.ui.kent.demo.recyclerview.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.android.ui.kent.demo.recyclerview.scroll.ScrollObserver;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;


public class FocusableQuickRecyclerView extends RecyclerView {

    //是否支持横向移出
    private boolean mCanFocusOutHorizontal = true;
    //是否支持纵向移出
    private boolean mCanFocusOutVertical = true;
    //是否支持記憶最後焦點
    private boolean mCanKeepLastFocus = true;
    //是否支持边界移动查找 (左键寻找上排最末位、右键寻找下排第一位)
    private boolean mCanBoundarySearchNext = true;
    //获焦后变化 Descendant 策略
    private boolean mGainFocusChangeDescendant;
    //是否支持記憶选中焦点(半落焦)
    private boolean mCanKeepSelectedFocus;


    private FocusQuickAdapter mAdapter;

    //焦点移出recyclerview的事件监听
    private FocusLostListener mFocusLostListener;
    //焦点移入recyclerview的事件监听
    private FocusGainListener mFocusGainListener;
    //原生焦点移入移出事件監聽
    private FocusChangedListener mOnFocusChangedListener;

    //当前位置
    private int mCurrentFocusPosition;
    //选中位置(半落焦)
    private int mSelectedPosition = -1;

    private boolean mIsDebugMode;


    private boolean mIsEnableRxScrollVertical;
    private int mItemHeight;
    private ScrollObserver mScrollObserver;
    private boolean mActionUp;
    private Disposable mDisposable;
    SnapHelper mSnapHelperCenter;
    private Integer mHeight;

    public FocusableQuickRecyclerView(Context context) {
        super(context);
        init();
    }

    public FocusableQuickRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FocusableQuickRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.mAdapter = (FocusQuickAdapter) getAdapter();
        if (mAdapter != null) {
            this.setCurrentPosition(mAdapter.getDefaultPosition());
        }
    }

    public void setCurrentPosition(int position) {
        this.mCurrentFocusPosition = position;
    }

    public int getCurrentFocusPosition() {
        return mCurrentFocusPosition;
    }

    public void remove(int position) {
        mAdapter.remove(position);
    }


    public void setCanFocusOutHorizontal(boolean bln) {
        this.mCanFocusOutHorizontal = bln;
    }

    public void setCanFocusOutVertical(boolean bln) {
        this.mCanFocusOutVertical = bln;
    }

    public void setCanKeepLastFocus(boolean canKeepLastFocus) {
        this.mCanKeepLastFocus = canKeepLastFocus;
    }

    public void setCanBoundarySearchNext(boolean bln) {
        this.mCanBoundarySearchNext = bln;
    }

    public void setGainFocusChangeDescendant(boolean bln) {
        this.mGainFocusChangeDescendant = bln;
        if (bln) {
            setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        }
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        timberLog("fqrv/onFocusChanged gainFocus = %s", gainFocus);
        if (getLayoutManager() == null) {
            super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
            return;
        }

        if(mOnFocusChangedListener != null){
            mOnFocusChangedListener.onFocusChanged(gainFocus, direction);
        }

        if (gainFocus) {

            if (mGainFocusChangeDescendant) {
                setDescendantFocusability(RecyclerView.FOCUS_AFTER_DESCENDANTS);
            }
            if (mFocusGainListener != null) {
                mFocusGainListener.onFocusGain(null, null);
            }

            View viewCurrFocus = getLayoutManager().findViewByPosition(mCurrentFocusPosition);
            if (viewCurrFocus != null) {
                viewCurrFocus.requestFocus();
            }

        }

        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    //覆写focusSearch寻焦策略
    @Override
    public View focusSearch(View focused, int direction) {
        View view = super.focusSearch(focused, direction);
        timberLog("fqrv/focusSearch() position = %s, mAdapter.getItemCount() = %s", mCurrentFocusPosition, mAdapter.getItemCount());

        if (mCanBoundarySearchNext) {
            // 内部焦点移动拦截策略
            if (direction == View.FOCUS_LEFT && mCurrentFocusPosition > 0) {
                View view1 = getItemViewByPosition(mCurrentFocusPosition - 1, direction);
                return view1;
            } else if (direction == View.FOCUS_RIGHT && mCurrentFocusPosition < mAdapter.getItemCount()) {
                View view1 = getItemViewByPosition(mCurrentFocusPosition + 1, direction);
                return view1;
            }
        }

        //移出 rv 拦截策略
        if (view != null) {

            //该方法返回焦点view所在的父view,如果是在recyclerview之外，就会是null.所以根据是否是null,来判断是否是移出了recyclerview
            View nextFocusItemView = findContainingItemView(view);
            timberLog("fqrv/nextFocusItemView = %s", nextFocusItemView);

            if (nextFocusItemView == null) {
                if (!mCanFocusOutVertical && (direction == View.FOCUS_DOWN || direction == View.FOCUS_UP)) {
                    //屏蔽焦点纵向移出 rv
                    return focused;
                } else if (!mCanFocusOutHorizontal && (direction == View.FOCUS_LEFT || direction == View.FOCUS_RIGHT)) {
                    //屏蔽焦点横向移出 rv
                    return focused;
                } else {
                    //调用移出的监听
                    if (mGainFocusChangeDescendant) {
                        setDescendantFocusability(RecyclerView.FOCUS_BLOCK_DESCENDANTS);
                    }
                    if (mFocusLostListener != null) {
                        mFocusLostListener.onFocusLost(focused, direction);
                    }
                    return view;
                }

            }

        }

        return view;
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        timberLog("fqrv/requestChildFocus() nextchild= " + child + ",focused = " + focused);

        if (!hasFocus()) {
            //recyclerview 子view 重新获取焦点，调用移入焦点的事件监听
            if (mFocusGainListener != null) {
                mFocusGainListener.onFocusGain(child, focused);
            }
        }

        super.requestChildFocus(child, focused);//执行过super.requestChildFocus之后hasFocus会变成true
        int position = getChildViewHolder(child).getAdapterPosition();
        this.setCurrentPosition(position);
        timberLog("fqrv/focusPos = " + mCurrentFocusPosition);
    }

    /**
     * 由外部焦点进入 RevyclerView 会调用此方法
     *
     * @param views
     * @param direction
     * @param focusableMode
     */
    @Override
    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        if (getLayoutManager() == null) {
            return;
        }
        View viewCurrFocus = getLayoutManager().findViewByPosition(mCurrentFocusPosition);
        View viewSelectedFocus = null;
        if (mCanKeepSelectedFocus && mSelectedPosition != -1) {
            viewSelectedFocus = getLayoutManager().findViewByPosition(mSelectedPosition);
        }

        timberLog("fqrv/addFocusables() mCurrentFocusPosition = %s, view= %s", mCurrentFocusPosition, viewCurrFocus);
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);

        if (this.hasFocus() || mCurrentFocusPosition < 0 || (viewCurrFocus == null && viewSelectedFocus == null)) {
            super.addFocusables(views, direction, focusableMode);
        } else if (mCanKeepSelectedFocus && viewSelectedFocus != null) {
            views.add(viewSelectedFocus);
        } else if (mCanKeepLastFocus) {
            //实现焦点记忆的关键代码
            views.add(viewCurrFocus);
        } else {
            super.addFocusables(views, direction, focusableMode);
        }
    }

    private View getItemViewByPosition(int position, int direction) {
        View view = getLayoutManager().findViewByPosition(position);

        if (view == null) {
            return view;
        }

        //若移动中遇到 非 ItemView ，如 Selection Header 需往右 or 往左 +1 position
        if (View.FOCUS_RIGHT == direction) {
            if (!view.isFocusable() && position < getAdapter().getItemCount()) {
                View rView = getLayoutManager().findViewByPosition(position);
                return rView != null ? rView : view;
            }

        }

        if (View.FOCUS_LEFT == direction) {
            if (!view.isFocusable() && position > 0) {
                View lView = getLayoutManager().findViewByPosition(position);
                return lView != null ? lView : view;
            }
        }

        return view;
    }

    public void scrollTop() {
        smoothScrollToPosition(mAdapter.getDefaultPosition());
    }


    @Override
    public void smoothScrollToPosition(int position) {
        if (mAdapter.getItemCount() == 0 || position < 0) {
            return;
        }
        this.setCurrentPosition(position);
        super.smoothScrollToPosition(position);
    }

    @Override
    public void scrollToPosition(int position) {
        if (mAdapter.getItemCount() == 0 || position < 0) {
            return;
        }
        this.setCurrentPosition(position);
        super.scrollToPosition(position);
    }


    /**
     * 回调方法
     */
    public interface FocusLostListener {
        void onFocusLost(View lastFocusChild, int direction);
    }

    public interface FocusGainListener {
        void onFocusGain(View chld, View focued);
    }

    public interface FocusChangedListener {
        void onFocusChanged(boolean gainFocus, int direction);
    }

    public void setDebugMode(boolean bln) {
        this.mIsDebugMode = bln;
    }

    private void timberLog(String msg) {
        if (mIsDebugMode) {
            Timber.d(msg);
        }
    }

    private void timberLog(String msg, Object... obj) {
        if (mIsDebugMode) {
            switch (obj.length) {
                case 1:
                    Timber.d(msg, obj[0]);
                    break;
                case 2:
                    Timber.d(msg, obj[0], obj[1]);
                    break;
                case 3:
                    Timber.d(msg, obj[0], obj[1], obj[2]);
                    break;
            }
        }
    }

    public void setFocusLostListener(FocusLostListener mFocusLostListener) {
        this.mFocusLostListener = mFocusLostListener;
    }

    public void setFocusGainListener(FocusGainListener mFocusGainListener) {
        this.mFocusGainListener = mFocusGainListener;
    }

    public void setFocus(int pos) {
        if (pos > mAdapter.getItemCount() - 1) {
            return;
        }

        View view = getLayoutManager().findViewByPosition(pos);
        if (view != null) {
            if (!view.isFocusable()) {
                View view1 = view.findViewById(mAdapter.getItemViewIdRes());
                if (view1 != null) {
                    view1.requestFocus();
                }
            } else {
                view.requestFocus();
            }
        }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        /**
         * >> 防止跑焦 <<
         * 预查下一个 View 能否 Focus
         */
//        if (getLayoutManager() instanceof GridLayoutManager) {
//            if (event.getAction() == KeyEvent.ACTION_DOWN &&
//                    event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
//
//                int i = mCurrentFocusPosition + ((GridLayoutManager) getLayoutManager()).getSpanCount();
//                if (i + 1 <= mAdapter.getDataItemCount()) {
//                    if (getLayoutManager().findViewByPosition(i) == null) {
//                        timberLog("fqrv/KeyEvent KEYCODE_DPAD_DOWN next View is null");
//                        return true;
//                    }
//                }
//            }
//        } else if (getLayoutManager() instanceof LinearLayoutManager &&
//                ((LinearLayoutManager) getLayoutManager()).getOrientation() == LinearLayoutManager.HORIZONTAL) {
//            if (event.getAction() == KeyEvent.ACTION_DOWN &&
//                    event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
//
//                int i = mCurrentFocusPosition + 1;
//                if (i <= mAdapter.getDataItemCount()) {
//                    if (getLayoutManager().findViewByPosition(i) == null) {
//                        timberLog("fqrv/KeyEvent KEYCODE_DPAD_RIGHT next View is null");
//                        return true;
//                    }
//                }
//            } else if (event.getAction() == KeyEvent.ACTION_DOWN &&
//                    event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
//
//                int i = mCurrentFocusPosition - 1;
//                if (i >= 0) {
//                    if (getLayoutManager().findViewByPosition(i) == null) {
//                        timberLog("fqrv/KeyEvent KEYCODE_DPAD_LEFT next View is null");
//                        return true;
//                    }
//                }
//            }
//        }


        if (event.getAction() == KeyEvent.ACTION_UP) {
            mActionUp = true;
        } else if (event.getAction() == KeyEvent.ACTION_DOWN) {
            mActionUp = false;
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP
                    || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {

                Timber.d(">> dispatchKeyEvent KeyEvent.KEYCODE RIGHT or LEFT");

                //计算 item 高度
                if (mHeight == null) {
                    int pos = getCurrentFocusPosition();
                    Timber.d(">> pos = %s", pos);
                    View view = getLayoutManager().findViewByPosition(pos);

                    if (view != null) {
                        float y = view.getY();
                        mHeight = view.getBottom() - view.getTop();
                    }

                    if (mHeight == null) {
                        return true;
                    }
                }

                //处理左右方向
                int y = event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN ? mHeight : -mHeight;

//                //头末4比焦点处理
//                int firstPos = ((LinearLayoutManager) mainRgetLayoutManager()).findFirstCompletelyVisibleItemPosition();
//                int lastPos = ((LinearLayoutManager) mainRv.getLayoutManager()).findLastVisibleItemPosition();
//                int count = mainRv.getAdapter().getItemCount();
//                if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT && lastPos == count - 1) {
//                    int targetPos;
//                    if (mainRv.getCurrentFocusPosition() < count - 4) {
//                        targetPos = count - 4;
//                    } else {
//                        targetPos = mainRv.getCurrentFocusPosition() + 1;
//                    }
//                    mainRv.setFocus(targetPos);
//                    return true;
//                } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT && firstPos == 0) {
//                    int targetPos;
//                    if (mainRv.getCurrentFocusPosition() > 4) {
//                        targetPos = 4;
//                    } else {
//                        targetPos = mainRv.getCurrentFocusPosition() - 1;
//                    }
//                    mainRv.setFocus(targetPos);
//                    return true;
//                }


                //先滚动后获焦
                if (mDisposable != null) {
                    mDisposable.dispose();
                }
                mScrollObserver.rxSmoothScroll(this, 0, y)
//                        .filter(new Predicate() {
//                            @Override
//                            public boolean test(Object o) throws Exception {
//                                return !mActionUp;
//                            }
//                        })
//                        .delay(300, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                mDisposable = d;
                            }

                            @Override
                            public void onNext(Object value) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                Timber.d(">> onComplete");
                                if (mActionUp) {
                                    mSnapHelperCenter.findSnapView(getLayoutManager()).requestFocus();
                                }
                            }
                        });
                return true;

//                }

            }
        }


        return super.dispatchKeyEvent(event);
    }

    public void setCanKeepSelectedFocus(boolean bln) {
        this.mCanKeepSelectedFocus = bln;
    }

    public void setSelectedPosition(int pos) {
        this.mSelectedPosition = pos;
    }

    public void addOnFocusChangedListener(FocusChangedListener listener){
        this.mOnFocusChangedListener = listener;
    }

    public void enableRxScrollVertical() {
        this.mIsEnableRxScrollVertical = true;
        this.mScrollObserver = new ScrollObserver();
        mScrollObserver.bindRv(this);
        mSnapHelperCenter = new LinearSnapHelper();
        mSnapHelperCenter.attachToRecyclerView(this);
    }
}
