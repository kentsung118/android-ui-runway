package com.android.ui.kent.demo.ui_response.touch_move;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import butterknife.ButterKnife;
import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

public class UITouchMove2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_touch_move_2);
        ButterKnife.bind(this);
        initToolbar();

    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("UI TouchMove2");
        this.enableBackButton();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, UITouchMove2Activity.class);
        activity.startActivity(intent);
    }

}
