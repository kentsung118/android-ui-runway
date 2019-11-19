package com.android.ui.kent.demo.framwork.glide;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.ButterKnife;

public class GlideActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_framework);
        ButterKnife.bind(this);

        initToolbar();

        ImageView imageView1 = findViewById(R.id.iv_image1);
        ImageView imageView2 = findViewById(R.id.iv_image2);
        ImageView imageView3 = findViewById(R.id.iv_image3);

        Glide.with(this).load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3394263821,1263517319&fm=26&gp=0.jpg").loadding(R.mipmap.ic_launcher).into(imageView1);
        Glide.with(this).load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=473619872,1560375257&fm=11&gp=0.jpg").loadding(R.mipmap.ic_launcher).into(imageView2);
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3327248454,849738544&fm=26&gp=0.jpg").loadding(R.mipmap.ic_launcher).into(imageView3);

    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("Glide Framework");
        this.enableBackButton();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, GlideActivity.class);
        activity.startActivity(intent);
    }
}
