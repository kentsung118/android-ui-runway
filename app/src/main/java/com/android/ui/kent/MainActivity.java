package com.android.ui.kent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.ui.kent.activity.AndroidArcActivity;
import com.android.ui.kent.activity.AndroidUIActivity;
import com.android.ui.kent.activity.NetworkActivity;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.alarm.AlarmActivity;
import com.android.ui.kent.demo.architecture.lifecycle.LifeCycleActivity;
import com.android.ui.kent.demo.architecture.room.RoomActivity;
import com.android.ui.kent.demo.cardview.CardActivity;
import com.android.ui.kent.demo.drawer.DrawerActivity;
import com.android.ui.kent.demo.eventbus.EventBusActivity;
import com.android.ui.kent.demo.indicator.IndicatorActivity;
import com.android.ui.kent.demo.layout.contraint.ConstraintLayoutActivity;
import com.android.ui.kent.demo.layout.style.StyleSelectorActivity;
import com.android.ui.kent.demo.listview.ListViewActivity;
import com.android.ui.kent.demo.mvvm.view.MvvmActivity;
import com.android.ui.kent.demo.network.okhttp.OKHttpActivity;
import com.android.ui.kent.demo.network.retrofit.RetrofitActivity;
import com.android.ui.kent.demo.network.rxjava.RxJavaActivity;
import com.android.ui.kent.demo.recyclerview.RecyclerViewActivity;
import com.android.ui.kent.demo.recyclerview.loadmore.LoadMoreActivity;
import com.android.ui.kent.demo.recyclerview.multi_layer.MultiLayerActivity;
import com.android.ui.kent.demo.recyclerview.scroll.RvScrollActivity;
import com.android.ui.kent.demo.tab.TabActivity;
import com.android.ui.kent.demo.udp.UdpActivity;
import com.android.ui.kent.demo.ui_response.UIResponseActivity;
import com.android.ui.kent.demo.widget.dialog.DialogActivity;
import com.android.ui.kent.demo.widget.view.CustomizeViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        init();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("UIRunway");
    }

    private void init() {

        List<String> list = new ArrayList<>();
        list.add(getString(R.string.main_type_android_ui));
        list.add(getString(R.string.main_type_android_arc));
        list.add(getString(R.string.main_type_network));
        list.add(getString(R.string.main_type_RxJava));

        ArrayAdapter<String> listAdapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(onItemClickListener);
    }

    private ListView.OnItemClickListener onItemClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Activity activity = MainActivity.this;

            final String actionName = ((TextView) view).getText().toString();

            if (actionName.equals(activity.getString(R.string.main_type_android_ui))) {
                AndroidUIActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_type_android_arc))) {
                AndroidArcActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_type_network))) {
                NetworkActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_type_RxJava))) {
                RxJavaActivity.launch(activity);
            }
        }
    };

}

