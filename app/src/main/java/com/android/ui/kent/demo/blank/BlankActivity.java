package com.android.ui.kent.demo.blank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Kent on 2016/10/4.
 */

public class BlankActivity extends BaseActivity {

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
        Intent intent = new Intent(activity, BlankActivity.class);
        activity.startActivity(intent);
    }

}
