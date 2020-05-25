package com.android.ui.kent.demo.indicator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.viewpagerindicator.CirclePageIndicator;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kent on 2016/10/4.
 */

public class IndicatorActivity extends BaseActivity{

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    final int LAST_PAGE_INDEX = 3;
    int width;
    private MyAdapter myAdapter;

    private Handler timerHandler = new Handler();
    private Runnable adRunnable;
    private static final long AD_DELAY = 3000L;
    private boolean isADRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);

        ButterKnife.bind(this);
        initToolbar();
        init();
    }

    private void initToolbar(){
        this.setupToolbar();
        this.setToolbarTitle("CirclePageIndicator + ViewPager");
        this.enableBackButton();
    }

    private void init(){


        myAdapter = new MyAdapter(getSupportFragmentManager());
        myAdapter.addFragment(MyFragment.newInstance(new ItemVO("內容一", R.mipmap.ic_launcher)));
        myAdapter.addFragment(MyFragment.newInstance(new ItemVO("內容二", R.mipmap.ic_launcher)));
        myAdapter.addFragment(MyFragment.newInstance(new ItemVO("內容三", R.mipmap.ic_launcher)));

        viewPager.setAdapter(myAdapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        stopPlayAD();
                        break;
                    case MotionEvent.ACTION_UP:
                        startPlayAD();
                        break;
                }
                return false;
            }
        });


        indicator.setViewPager(viewPager);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int specialCase = LAST_PAGE_INDEX;
                if (position == specialCase || (position == specialCase - 1 && positionOffsetPixels > width / 2)) {
                    indicator.setPageColor(getResources().getColor(R.color.main_red));
                    indicator.setStrokeColor(Color.WHITE);
                    indicator.setStrokeWidth(1);
                    indicator.setFillColor(Color.WHITE);
                } else {
                    indicator.setPageColor(getResources().getColor(R.color.light_gray));
                    indicator.setStrokeWidth(0);
                    indicator.setFillColor(getResources().getColor(R.color.main_red));  //#7Fff1111
                }
                Log.d("IndicatorActivity", "pos:" + position + ", " + positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        adRunnable = new AdRunnable();
        startPlayAD();

    }

    private class AdRunnable implements Runnable{
        @Override
        public void run() {
            if (myAdapter.getCount() != 0) {
                viewPager.setCurrentItem((viewPager.getCurrentItem() + 1)
                        % myAdapter.getCount(), true);
                timerHandler.postDelayed(this, AD_DELAY);
            }
        }
    }

    private void startPlayAD() {
        if (!isADRunning) {
            timerHandler.postDelayed(adRunnable, AD_DELAY);
            isADRunning = true;
        }
    }

    private void stopPlayAD() {
        timerHandler.removeCallbacks(adRunnable);
        isADRunning = false;
    }


    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, IndicatorActivity.class);
        activity.startActivity(intent);
    }
}
