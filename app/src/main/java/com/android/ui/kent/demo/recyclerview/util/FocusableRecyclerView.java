package com.android.ui.kent.demo.recyclerview.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import timber.log.Timber;


/**
 * Created by KentSong on 2018/6/11.
 * <p>
 * <p>
 * 通用型 RecyclerView
 * - 支持记忆焦点功能
 * - 焦点由 FocusableRecyclerView 控管
 * - 支持设置封闭水平、垂直移出 RecyclerView
 * <p>
 */
public class FocusableRecyclerView extends RecyclerView {

    //是否可以横向移出
    private boolean mCanFocusOutHorizontal;
    //是否可以纵向移出
    private boolean mCanFocusOutVertical;
    //是否支持記憶最後焦點
    private boolean mCanKeepLastFocus = true;

    private FocusableAdapter mAdapter;
    private Integer mDefaultIndex;
    private int mCurrentIndex;

    private OnItemFocusListener mOnItemFocusListener;
    private OnKeyDownListener mOnKeyDownListener;
    private KeyEventInterceptor mKeyEventInterceptor;


    public FocusableRecyclerView(Context context) {
        super(context);
        init();
    }

    public FocusableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FocusableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
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

        if (mDefaultIndex != null) {
            this.mCurrentIndex = mDefaultIndex;
            focusItem(mCurrentIndex);
        }
    }

    public void setDefaultIndex(Integer defaultIndex) {
        this.mDefaultIndex = defaultIndex;
    }

    public void focusItem(int index) {
        focusItem(index, 0);
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
        this.mCurrentIndex = currentIndex;
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    public void setCanFocusOutVertical(boolean bln) {
        this.mCanFocusOutVertical = bln;
    }


    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        Timber.d(">> FocusableRecyclerView gainFocus = %s", gainFocus);
        if (gainFocus) {
            focusItem(mCurrentIndex, direction);
        } else {
            unFocusItem(mCurrentIndex);
        }
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (mKeyEventInterceptor == null) {
            return super.dispatchKeyEvent(event);
        }

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT && !mKeyEventInterceptor.canMoveLeft()) {
                return true;
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT && !mKeyEventInterceptor.canMoveRight()) {
                return true;
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP && !mKeyEventInterceptor.canMoveUp()) {
                return true;
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN && !mKeyEventInterceptor.canMoveDown()) {
                return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        boolean result = false;
        if (getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) getLayoutManager();
            if (manager.getOrientation() == RecyclerView.HORIZONTAL) {
                result = onKeyDownGirdLayoutHorizontal(keyCode);
            } else {
                result = onKeyDownGirdLayoutVertical(keyCode);
            }
        } else if (getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
            if (manager.getOrientation() == RecyclerView.HORIZONTAL) {
                result = onKeyDownLinearLayoutHorizontal(keyCode);
            } else {
                result = onKeyDownLinearLayoutVertical(keyCode);
            }
        }

        return result || super.onKeyDown(keyCode, event);
    }

    private boolean onKeyDownLinearLayoutHorizontal(int keyCode) {
        int itemCount = mAdapter.getItemCount();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (mCurrentIndex < itemCount - 1) {
                    mCurrentIndex++;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    callbackOnItemFocus(mCurrentIndex, View.FOCUS_RIGHT);
                    callbackOnKeyDown(keyCode, mCurrentIndex);
                    return true;
                } else {
                    if (!mCanFocusOutHorizontal) {
                        return true;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (mCurrentIndex > 0) {
                    mCurrentIndex--;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    callbackOnItemFocus(mCurrentIndex, View.FOCUS_LEFT);
                    callbackOnKeyDown(keyCode, mCurrentIndex);
                    return true;
                } else {
                    if (!mCanFocusOutHorizontal) {
                        return true;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                return true;
        }

        return false;
    }

    private boolean onKeyDownLinearLayoutVertical(int keyCode) {
        int itemCount = mAdapter.getItemCount();


        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (mCurrentIndex < itemCount - 1) {
                    mCurrentIndex++;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    callbackOnItemFocus(mCurrentIndex, View.FOCUS_RIGHT);
                    callbackOnKeyDown(keyCode, mCurrentIndex);
                    return true;
                } else {
                    if (!mCanFocusOutVertical) {
                        return true;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (mCurrentIndex > 0) {
                    mCurrentIndex--;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    callbackOnItemFocus(mCurrentIndex, View.FOCUS_RIGHT);
                    callbackOnKeyDown(keyCode, mCurrentIndex);
                    return true;
                } else {
                    if (!mCanFocusOutVertical) {
                        return true;
                    }
                }
                break;
        }


        return false;
    }

    private boolean onKeyDownGirdLayoutHorizontal(int keyCode) {
        int itemCount = mAdapter.getItemCount();
        int spanCount = ((GridLayoutManager) getLayoutManager()).getSpanCount();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (mCurrentIndex < itemCount - 1) {
                    mCurrentIndex++;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (mCurrentIndex > 0) {
                    mCurrentIndex--;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (mCurrentIndex < spanCount) {
                    return true;
                } else {
                    mCurrentIndex -= spanCount;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (mCurrentIndex + spanCount >= itemCount) {
                    return true;
                } else {
                    mCurrentIndex += spanCount;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
        }
        return false;
    }

    private boolean onKeyDownGirdLayoutVertical(int keyCode) {
        int itemCount = mAdapter.getItemCount();
        int spanCount = ((GridLayoutManager) getLayoutManager()).getSpanCount();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (mCurrentIndex + spanCount >= itemCount) {
                    return true;
                } else {
                    mCurrentIndex += spanCount;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
            case KeyEvent.KEYCODE_DPAD_UP:
                if (mCurrentIndex < spanCount) {
                    return true;
                } else {
                    mCurrentIndex -= spanCount;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (mCurrentIndex > 0) {
                    mCurrentIndex--;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (mCurrentIndex < itemCount - 1) {
                    mCurrentIndex++;
                    mAdapter.setItemFocused(mCurrentIndex);
                    smoothScrollToPosition(mCurrentIndex);
                    return true;
                }
                break;
        }
        return false;
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


}
