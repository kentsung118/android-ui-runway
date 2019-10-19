package com.android.ui.kent.demo.widget.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kent Song on 2018/12/22.
 */
public class MarqueeViewActivity extends BaseActivity {

    @BindView(R.id.marquee_tv)
    MarqueeTextView marqueeTv;
    @BindView(R.id.normal_tv)
    TextView normalTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee_view);
        ButterKnife.bind(this);


        marqueeTv.setText("测试马灯，通知....测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...123");
        marqueeTv.setRndDuration(30000);
        marqueeTv.startScroll();

        normalTv.setText("测试马灯，通知....测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...测试马灯，通知...123");
        normalTv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        normalTv.setMarqueeRepeatLimit(3);
        normalTv.setSelected(true);



//        marqueeTv.setOnMarqueeCompleteListener(new MarqueeTextView.OnMarqueeCompleteListener() {
//            @Override
//            public void onMarqueeComplete() {
//                Timber.d(">> OnMarqueeComplete");
//            }
//        });

    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MarqueeViewActivity.class);
        activity.startActivity(intent);
    }

    private String genSpaceWhite(int count) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i++ < count) {
            sb.append(" ");
        }
        return sb.toString();
    }

}
