package com.android.ui.kent.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.ui.kent.R;


public class TabItemView extends LinearLayout {

    private int mResId;
    private String mContent;

    public TabItemView(Context context) {
        super(context);
        init();
    }

    public TabItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabItemView, 0, 0);
        mResId = typedArray.getResourceId(R.styleable.TabItemView_tab_img, 0);
        mContent = typedArray.getString(R.styleable.TabItemView_tab_content);
        typedArray.recycle();
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setClickable(true);
        setBackgroundColor(Color.parseColor("#33ffffff"));

        addIcon();
        addText();
    }

    private void addIcon() {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(56, 56);
        layoutParams.leftMargin = 30;
        layoutParams.gravity = Gravity.CENTER;
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(mResId);
        addView(imageView);
    }

    private void addText() {
        TextView textView = new TextView(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 30;
        layoutParams.leftMargin = 30;
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
        textView.setTextColor(Color.BLACK);
        textView.setText(mContent);
        textView.setGravity(TEXT_ALIGNMENT_CENTER);
        addView(textView);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        if (gainFocus) {
            setBackgroundColor(getResources().getColor(R.color.google_blue));
        } else {
            setBackgroundColor(getResources().getColor(R.color.google_yellow));
        }
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }
}
