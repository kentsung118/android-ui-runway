package com.android.ui.kent.demo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Kent Song on 2019/5/7.
 */
public class CustomizeViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);
        ButterKnife.bind(this);

        initToolbar();
        init();

    }

    private void initToolbar(){
        this.setupToolbar();
        this.setToolbarTitle(getString(R.string.main_action_customize_view));
        this.enableBackButton();
    }

    private void init(){
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, CustomizeViewActivity.class);
        activity.startActivity(intent);
    }
}
