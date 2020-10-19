package com.kent.android.slim.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

/**
 * Created by songzhukai on 2020/10/19.
 */
public class ConstransLayoutDemoActivity extends AppCompatActivity {

    Button mButton1;
    Button mButton2;
    Button mButton3;
    ConstraintLayout mConstraintLayout;
    TextView mTitle;
    TextView mSubTitle;
    ImageView mIcon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constranslayout);

        initView();


    }

    private void initView() {
        mButton1 = findViewById(R.id.btn1);
        mButton2 = findViewById(R.id.btn2);
        mButton3 = findViewById(R.id.btn3);
        mConstraintLayout = findViewById(R.id.constraint_layout);
        mTitle = findViewById(R.id.title);
        mTitle = findViewById(R.id.sub_title);
        mIcon = findViewById(R.id.icon);


        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //單純添加 Margin
                ConstraintSet set = new ConstraintSet();
                set.clone(mConstraintLayout);
                set.setMargin(R.id.title, 4, 80);
//                set.setMargin(R.id.id, 3,imageTopMare+Utils.dip2px(20));
                set.applyTo(mConstraintLayout);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //約束關係
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(mConstraintLayout);
                constraintSet.setMargin(R.id.title, 4, 0);
                constraintSet.connect(R.id.title, ConstraintSet.TOP, R.id.icon, ConstraintSet.TOP, 0);
                constraintSet.connect(R.id.title, ConstraintSet.BOTTOM, R.id.icon, ConstraintSet.BOTTOM, 0);
                constraintSet.applyTo(mConstraintLayout);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //約束關係
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(mConstraintLayout);
                //解除約束
                constraintSet.connect(R.id.title, ConstraintSet.TOP, -1, ConstraintSet.TOP, 0);
//                constraintSet.connect(R.id.title, ConstraintSet.BOTTOM, R.id.sub_title, ConstraintSet.TOP, 0);
                constraintSet.applyTo(mConstraintLayout);
            }
        });
    }


}
