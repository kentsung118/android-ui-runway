package com.android.ui.kent.demo.view;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("kentsong", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("kentsong", "onRestoreInstanceState");

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("kentsong", "onNewIntent");

        super.onNewIntent(intent);
    }

    @Override
    protected void onPause() {
        Log.d("kentsong", "onPause");

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("kentsong", "onStop");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("kentsong", "onDestroy");


        super.onDestroy();
    }
}
