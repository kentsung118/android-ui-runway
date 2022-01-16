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
import com.android.ui.kent.demo.alarm.AlarmActivity;
import com.android.ui.kent.demo.architecture.room.RoomActivity;
import com.android.ui.kent.demo.db.GreenDaoActivity;
import com.android.ui.kent.demo.eventbus.EventBusActivity;
import com.android.ui.kent.demo.mvvm.view.MvvmActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kent Song on 2019/4/14.
 */
public class AndroidArcActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_arc);
        ButterKnife.bind(this);

        initToolbar();
        init();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("Android Arc");
    }

    private void init() {

        List<String> list = new ArrayList<>();
        list.add(getString(R.string.main_action_mvvm));
        list.add(getString(R.string.main_action_arc_room));
        list.add(getString(R.string.main_action_alarm_manager));
        list.add(getString(R.string.main_action_EventBus));
        list.add(getString(R.string.main_action_greendao));
        list.add(getString(R.string.main_action_okhttp));


        ArrayAdapter<String> listAdapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(onItemClickListener);
    }

    private ListView.OnItemClickListener onItemClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Activity activity = AndroidArcActivity.this;

            final String actionName = ((TextView) view).getText().toString();

            if (actionName.equals(activity.getString(R.string.main_action_alarm_manager))) {
                AlarmActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_EventBus))) {
                EventBusActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_arc_room))) {
                RoomActivity.launch(activity);
            } else if (actionName.endsWith(getString(R.string.main_action_mvvm))) {
                MvvmActivity.launch(activity);
            } else if (actionName.endsWith(getString(R.string.main_action_greendao))) {
                GreenDaoActivity.launch(activity);
            }
        }
    };

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, AndroidArcActivity.class);
        activity.startActivity(intent);
    }
}
