package com.android.ui.kent.demo.ui_response.touch_move;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;
import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

public class UITouchMoveActivity extends BaseActivity {

//    @BindView(R.id.my_rv_1)
//    MyRecyclerView myRecyclerView1;
    @BindView(R.id.my_rv_2)
    MyRecyclerView myRecyclerView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_touch_move);
        ButterKnife.bind(this);
        initToolbar();



        ListAdapter adapter1 = new ListAdapter(genData(50));
        ListAdapter adapter2 = new ListAdapter(genData(50));

//        myRecyclerView1.setLayoutManager(new LinearLayoutManager(this, HORIZONTAL, false));
//        myRecyclerView1.setAdapter(adapter1);
        myRecyclerView2.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
        myRecyclerView2.setAdapter(adapter2);

        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("kentsung", "onClick");
                adapter2.notifyDataSetChanged();
            }
        });
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("UI TouchMove");
        this.enableBackButton();
    }

    private List<ListAdapter.ButtonVO> genData(int count) {
        List<ListAdapter.ButtonVO> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new ListAdapter.ButtonVO(i + "P"));
        }
        return list;
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, UITouchMoveActivity.class);
        activity.startActivity(intent);
    }

}
