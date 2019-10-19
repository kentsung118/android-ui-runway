package com.android.ui.kent.demo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;


import butterknife.ButterKnife;

/**
 * Created by Kent on 2016/10/5.
 */

public class CardActivity extends BaseActivity {


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
        this.setToolbarTitle("CardView");
        this.enableBackButton();
    }

    private void init(){
//        textView.setText("內容");
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, CardActivity.class);
        activity.startActivity(intent);
    }

}
