package com.android.ui.kent.demo.layout.contraint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import butterknife.ButterKnife;
import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.recyclerview.loadmore.LoadMoreActivity;

/**
 * Created by Kent on 2017/10/24.
 */

public class ConstraintLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_contraint);
        ButterKnife.bind(this);

        initToolbar();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.enableBackButton();
        this.setToolbarTitle("Constraint Layout");
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ConstraintLayoutActivity.class);
        activity.startActivity(intent);
    }

}
