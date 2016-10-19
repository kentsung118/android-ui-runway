package com.android.ui.kent.demo.indicator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kent on 2016/10/4.
 */

public class IndicatorActivity extends BaseActivity {

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;

    final int LAST_PAGE_INDEX = 3;
    int width;

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
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        adapter.addFragment(MyFragment.newInstance(new ItemVO("內容一", R.mipmap.ic_launcher)));
        adapter.addFragment(MyFragment.newInstance(new ItemVO("內容二", R.mipmap.ic_launcher)));
        adapter.addFragment(MyFragment.newInstance(new ItemVO("內容三", R.mipmap.ic_launcher)));

        viewPager.setAdapter(adapter);

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
    }


    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, IndicatorActivity.class);
        activity.startActivity(intent);
    }
}
