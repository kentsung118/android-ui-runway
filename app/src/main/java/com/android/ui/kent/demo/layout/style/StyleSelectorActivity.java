package com.android.ui.kent.demo.layout.style;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

/**
 * Created by Kent on 2017/11/6.
 */

public class StyleSelectorActivity extends BaseActivity {

    @BindView(R.id.btn1) Button btn1;
    @BindView(R.id.btn2) Button btn2;
    @BindView(R.id.txt1) TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_selector);
        ButterKnife.bind(this);

        initToolbar();
        init();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.enableBackButton();
        this.setToolbarTitle("Style Selector");
    }

    private void init(){
        btn1.setSelected(true);
        btn2.setSelected(false);

    }

    @OnClick({R.id.btn1, R.id.btn2})
    void onBtnClick(View v){
        if(v.getId() == R.id.btn1){
            btn1.setSelected(true);
            btn2.setSelected(false);
        } else{
            btn2.setSelected(true);
            btn1.setSelected(false);
        }

    }

    @OnClick(R.id.txt1)
    void onTextClick(View v){
        if(txt1.isSelected()){
            txt1.setSelected(false);
        } else{
            txt1.setSelected(true);
        }
    }



    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, StyleSelectorActivity.class);
        activity.startActivity(intent);
    }
}
