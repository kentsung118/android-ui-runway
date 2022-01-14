package com.android.ui.kent.demo.block;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.tab.ItemVO;
import com.android.ui.kent.demo.tab.TabFragment;
import com.android.ui.kent.demo.tab.TabPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kent on 2016/10/4.
 */

public class BlockActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black);

        ButterKnife.bind(this);
        initToolbar();
        init();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("Block Sample");
        this.enableBackButton();
    }

    private void init(){
    }


    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, BlockActivity.class);
        activity.startActivity(intent);
    }

}
