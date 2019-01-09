package com.android.ui.kent.demo.recyclerview.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.android.ui.kent.demo.recyclerview.scroll.ScrollObserver;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;


/**
 * Created by KentSong on 2018/6/11.
 * <p>
 * - 特性:
 * - 1. 先滾動後獲焦
 * - 2. child View 不獲得焦點，由 RecyclerView 控制 item UI (notifyItemChange)
 * - 3. 需配合 FocusableAdapter {@link FocusableAdapter}
 * <p>
 */
public class ScrollFirstRecyclerView extends RecyclerView {

    //是否可以横向移出
    private boolean mCanFocusOutHorizontal;
    //是否可以纵向移出
    private boolean mCanFocusOutVertical;
    //是否支持記憶最後焦點
    private boolean mCanKeepLastFocus = true;

    private FocusableAdapter mAdapter;
    private int mCurrentPos;

    private OnItemFocusListener mOnItemFocusListener;
    private OnKeyDownListener mOnKeyDownListener;
    private KeyEventInterceptor mKeyEventInterceptor;


    private boolean mIsEnableRxScrollVertical;
    private int mItemHeight;
    private ScrollObserver mScrollObserver;
    private boolean mActionUp;
    private Disposable mDisposable;
    SnapHelper mSnapHelperCenter;
    private Integer mHeight;


    public ScrollFirstRecyclerView(Context context) {
        super(context);
        init();
    }

    public ScrollFirstRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollFirstRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setItemAnimator(null);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.mAdapter = (FocusableAdapter) getAdapter();
    }


    public void focusItem(int position) {
        mCurrentPos = position;
        focusItem(position, 0);
    }

    private void focusItem(int index, int direction) {
        mAdapter.setItemFocused(index);
        callbackOnItemFocus(index, direction);
    }

    public void unFocusItem(int index) {
        mAdapter.setItemUnFocus(index);
    }

    private void unSelectItem(int index) {
        mAdapter.setItemUnSelect(index);
    }

    public void setCanFocusOutHorizontal(boolean bln) {
        this.mCanFocusOutHorizontal = bln;
    }

    public void setCurrentIndex(int currentIndex) {
        this.mCurrentPos = currentIndex;
    }

    public int getCurrentIndex() {
        return mCurrentPos;
    }

    public void setCanFocusOutVertical(boolean bln) {
        this.mCanFocusOutVertical = bln;
    }


    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        Timber.d(">> FocusableRecyclerView gainFocus = %s", gainFocus);
        if (gainFocus) {
            focusItem(mCurrentPos, direction);
        } else {
            unFocusItem(mCurrentPos);
        }
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_UP) {
            mActionUp = true;
        } else if (event.getAction() == KeyEvent.ACTION_DOWN) {
            mActionUp = false;
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP
                    || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {

                Timber.d(">> dispatchKeyEvent KeyEvent.KEYCODE RIGHT or LEFT");

                //计算 item 高度
                if (mHeight == null) {
                    int pos = 0;
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


                //先滚动后获焦
                if (mDisposable != null) {
                    mDisposable.dispose();
                }
                mScrollObserver.rxSmoothScroll(this, 0, y)
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
                                    View view = mSnapHelperCenter.findSnapView(getLayoutManager());
                                    int pos = getChildAdapterPosition(view);
                                    mCurrentPos = pos;
                                    mAdapter.setItemFocused(pos);
                                }
                            }
                        });
                return true;

                }

            }


        return super.dispatchKeyEvent(event);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }



    private void callbackOnItemFocus(int position, int direction) {
        if (mOnItemFocusListener != null) {
            mOnItemFocusListener.onItemFocused(position, direction);
        }
    }

    private void callbackOnKeyDown(int keyCode, int position) {
        if (mOnKeyDownListener != null) {
            mOnKeyDownListener.onKeyDown(keyCode, position);
        }
    }


    public void setOnItemFocusListener(OnItemFocusListener mOnItemFocusListener) {
        this.mOnItemFocusListener = mOnItemFocusListener;
    }

    public void setOnKeyDownListener(OnKeyDownListener mOnKeyDownListener) {
        this.mOnKeyDownListener = mOnKeyDownListener;
    }

    public void setKeyEventInterceptor(KeyEventInterceptor interceptor) {
        this.mKeyEventInterceptor = interceptor;
    }

    public void setCanKeepLastFocus(boolean bln) {
        this.mCanKeepLastFocus = bln;
    }

    /**
     * 回调方法
     */
    public interface OnItemFocusListener {
        void onItemFocused(int position, int direction);
    }

    public interface OnKeyDownListener {
        void onKeyDown(int keyCode, int position);
    }

    public interface KeyEventInterceptor {

        boolean canMoveLeft();

        boolean canMoveRight();

        boolean canMoveUp();

        boolean canMoveDown();
    }

    public void enableRxScrollVertical() {
        this.mIsEnableRxScrollVertical = true;
        this.mScrollObserver = new ScrollObserver();
        mScrollObserver.bindRv(this);
        mSnapHelperCenter = new LinearSnapHelper();
        mSnapHelperCenter.attachToRecyclerView(this);
    }


}
