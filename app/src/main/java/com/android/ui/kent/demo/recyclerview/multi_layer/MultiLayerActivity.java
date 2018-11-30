package com.android.ui.kent.demo.recyclerview.multi_layer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.recyclerview.multi_layer.model.MainVO;
import com.android.ui.kent.demo.recyclerview.util.FocusableQuickRecyclerView;
import com.android.ui.kent.demo.ui_response.UIResponseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kent Song on 2018/11/30.
 */
public class MultiLayerActivity extends BaseActivity {

    @BindView(R.id.main_rv)
    RecyclerView mainRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutil_recyclerview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        List<MainVO> list = new ArrayList<>();
        int num = 0;
        while (num < 15) {
            num++;
            list.add(new MainVO("回看更多"));
        }


        MainAdapter adapter = new MainAdapter(this, list);
        mainRv.setLayoutManager(new CenterLayoutManger(this));
        mainRv.setAdapter(adapter);
        adapter.setmOnItemFocusedListener(new MainAdapter.OnItemFocusedListener() {
            @Override
            public void onFocused(int position, int realPosition, View view) {
                mainRv.smoothScrollToPosition(position);
            }

            @Override
            public void onUnFocused(int position, int realPosition, View view) {

            }
        });


    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MultiLayerActivity.class);
        activity.startActivity(intent);
    }


}
