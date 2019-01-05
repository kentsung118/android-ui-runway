package com.android.ui.kent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.alarm.AlarmActivity;
import com.android.ui.kent.demo.architecture.lifecycle.LifeCycleActivity;
import com.android.ui.kent.demo.cardview.CardActivity;
import com.android.ui.kent.demo.drawer.DrawerActivity;
import com.android.ui.kent.demo.eventbus.EventBusActivity;
import com.android.ui.kent.demo.indicator.IndicatorActivity;
import com.android.ui.kent.demo.layout.contraint.ConstraintLayoutActivity;
import com.android.ui.kent.demo.layout.style.StyleSelectorActivity;
import com.android.ui.kent.demo.listview.ListViewActivity;
import com.android.ui.kent.demo.network.okhttp.OKHttpActivity;
import com.android.ui.kent.demo.network.retrofit.RetrofitActivity;
import com.android.ui.kent.demo.network.rxjava.RxJavaActivity;
import com.android.ui.kent.demo.recyclerview.RecyclerViewActivity;
import com.android.ui.kent.demo.recyclerview.loadmore.LoadMoreActivity;
import com.android.ui.kent.demo.recyclerview.multi_layer.MultiLayerActivity;
import com.android.ui.kent.demo.recyclerview.scroll.RvScrollActivity;
import com.android.ui.kent.demo.tab.TabActivity;
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
        list.add(getString(R.string.main_action_scroll_rv));
        list.add(getString(R.string.main_action_lifecycle_owner_view));
        list.add(getString(R.string.main_action_customize_view));
        list.add(getString(R.string.main_action_constraint_layout));
        list.add(getString(R.string.main_action_recycler_view));
        list.add(getString(R.string.main_action_multi_recycler_view));
        list.add(getString(R.string.main_action_listview));
        list.add(getString(R.string.main_action_alarm_manager));
        list.add(getString(R.string.main_action_navigation_drawer));
        list.add(getString(R.string.main_action_tablayout_view_pager));
        list.add(getString(R.string.main_action_CardView));
        list.add(getString(R.string.main_action_Retrofit));
        list.add(getString(R.string.main_action_OKHttp));
        list.add(getString(R.string.main_action_RxJava));
        list.add(getString(R.string.main_action_EventBus));
        list.add(getString(R.string.main_action_Dialog));
        list.add(getString(R.string.main_action_LoadMore));
        list.add(getString(R.string.main_action_circlepage_indicator_viewpager));
        list.add(getString(R.string.main_action_style_selector));
        list.add(getString(R.string.main_action_ui_response));


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

            if (actionName.equals(activity.getString(R.string.main_action_recycler_view))) {
                RecyclerViewActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_listview))) {
                ListViewActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_alarm_manager))) {
                AlarmActivity.launch(activity);
            } else if (actionName.equals(
                    activity.getString(R.string.main_action_navigation_drawer))) {
                DrawerActivity.launch(activity);
            } else if (actionName.equals(
                    activity.getString(R.string.main_action_tablayout_view_pager))) {
                TabActivity.launch(activity);
            } else if (actionName.equals(
                    activity.getString(R.string.main_action_circlepage_indicator_viewpager))) {
                IndicatorActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_CardView))) {
                CardActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_Retrofit))) {
                RetrofitActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_OKHttp))) {
                OKHttpActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_RxJava))) {
                RxJavaActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_EventBus))) {
                EventBusActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_Dialog))) {
                DialogActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_LoadMore))) {
                LoadMoreActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_constraint_layout))) {
                ConstraintLayoutActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_style_selector))) {
                StyleSelectorActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_ui_response))) {
                UIResponseActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_multi_recycler_view))) {
                MultiLayerActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_customize_view))) {
                CustomizeViewActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_lifecycle_owner_view))) {
                LifeCycleActivity.launch(activity);
            }else if (actionName.equals(getString(R.string.main_action_scroll_rv))) {
                RvScrollActivity.launch(activity);
            }
        }
    };

}

