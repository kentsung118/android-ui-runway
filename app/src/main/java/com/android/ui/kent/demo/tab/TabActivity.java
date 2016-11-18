package com.android.ui.kent.demo.tab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kent on 2016/10/4.
 */

public class TabActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        ButterKnife.bind(this);
        initToolbar();
        init();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("TabLayout + ViewPager");
        this.enableBackButton();
    }

    private void init(){
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), this);
        adapter.addFragment("Tab1", TabFragment.newInstance(new ItemVO("內容一", "https://drjaosdejw578.cloudfront.net/tw/static/1475598396979/assets/886/products/37581.png")));
        adapter.addFragment("Tab2", TabFragment.newInstance(new ItemVO("內容二", "https://drjaosdejw578.cloudfront.net/tw/static/1475598396979/assets/886/products/37828.png")));
        adapter.addFragment("Tab3", TabFragment.newInstance(new ItemVO("內容三", "https://drjaosdejw578.cloudfront.net/tw/static/1475598396979/assets/886/products/31363.png")));

        viewPager.setOffscreenPageLimit(3); //
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }


    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, TabActivity.class);
        activity.startActivity(intent);
    }

}
