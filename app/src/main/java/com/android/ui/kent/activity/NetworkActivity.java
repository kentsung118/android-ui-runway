package com.android.ui.kent.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.network.okhttp.OKHttpActivity;
import com.android.ui.kent.demo.network.retrofit.RetrofitActivity;
import com.android.ui.kent.demo.udp.UdpActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kent Song on 2019/4/14.
 */
public class NetworkActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);

        initToolbar();
        init();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("Network");
    }

    private void init() {

        List<String> list = new ArrayList<>();
        list.add(getString(R.string.main_action_udp));
        list.add(getString(R.string.main_action_Retrofit));
        list.add(getString(R.string.main_action_OKHttp));


        ArrayAdapter<String> listAdapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(onItemClickListener);
    }

    private final ListView.OnItemClickListener onItemClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Activity activity = NetworkActivity.this;

            String actionName = ((TextView) view).getText().toString();

            if (actionName.equals(activity.getString(R.string.main_action_Retrofit))) {
                RetrofitActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_OKHttp))) {
                OKHttpActivity.launch(activity);
            } else if (actionName.endsWith(getString(R.string.main_action_udp))) {
                UdpActivity.launch(activity);
            }
        }
    };

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, NetworkActivity.class);
        activity.startActivity(intent);
    }
}
