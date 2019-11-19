package com.android.ui.kent.activity;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.architecture.lifecycle.LifeCycleActivity;
import com.android.ui.kent.demo.drawer.DrawerActivity;
import com.android.ui.kent.demo.framwork.eventbus.EventBusOneActivity;
import com.android.ui.kent.demo.framwork.glide.GlideActivity;
import com.android.ui.kent.demo.framwork.okhttp.OkHttpActivity;
import com.android.ui.kent.demo.indicator.IndicatorActivity;
import com.android.ui.kent.demo.layout.contraint.ConstraintLayoutActivity;
import com.android.ui.kent.demo.layout.style.StyleSelectorActivity;
import com.android.ui.kent.demo.listview.ListViewActivity;
import com.android.ui.kent.demo.recyclerview.RecyclerViewActivity;
import com.android.ui.kent.demo.recyclerview.loadmore.LoadMoreActivity;
import com.android.ui.kent.demo.recyclerview.multi_layer.MultiLayerActivity;
import com.android.ui.kent.demo.recyclerview.scroll.RvScrollActivity;
import com.android.ui.kent.demo.reflect_ioc.ReflectIocActivity;
import com.android.ui.kent.demo.tab.TabActivity;
import com.android.ui.kent.demo.ui_response.touch_event.UIResponseActivity;
import com.android.ui.kent.demo.ui_response.touch_move.UITouchMove2Activity;
import com.android.ui.kent.demo.ui_response.touch_move.UITouchMoveActivity;
import com.android.ui.kent.demo.view.CardActivity;
import com.android.ui.kent.demo.view.CustomizeViewActivity;
import com.android.ui.kent.demo.view.TransitionDrawableActivity;
import com.android.ui.kent.demo.widget.dialog.DialogActivity;
import com.android.ui.kent.demo.widget.view.MarqueeViewActivity;
import com.android.ui.kent.rxjava.sample.anim.RxAnimActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kent Song on 2019/4/14.
 */
public class AndroidUIActivity extends BaseActivity {
    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_view);
        ButterKnife.bind(this);

        initToolbar();
        init();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("Android UI");
    }

    private void init() {

        List<String> list = new ArrayList<>();
        list.add(getString(R.string.main_action_glide_manual));
        list.add(getString(R.string.main_action_okhttp_manual));
        list.add(getString(R.string.main_action_eventbus_manual));
        list.add(getString(R.string.main_action_reflect_ioc));
        list.add(getString(R.string.main_action_ui_touch_move_2));
        list.add(getString(R.string.main_action_ui_touch_move));
        list.add(getString(R.string.main_action_transitiondrawable));
        list.add(getString(R.string.main_action_customize_view));
        list.add(getString(R.string.main_action_rxjava_anim));
        list.add(getString(R.string.main_action_scroll_rv));
        list.add(getString(R.string.main_action_lifecycle_owner_view));
        list.add(getString(R.string.main_action_marquee_view));
        list.add(getString(R.string.main_action_constraint_layout));
        list.add(getString(R.string.main_action_recycler_view));
        list.add(getString(R.string.main_action_multi_recycler_view));
        list.add(getString(R.string.main_action_listview));
        list.add(getString(R.string.main_action_navigation_drawer));
        list.add(getString(R.string.main_action_tablayout_view_pager));
        list.add(getString(R.string.main_action_CardView));
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
            Activity activity = AndroidUIActivity.this;

            final String actionName = ((TextView) view).getText().toString();
            if (actionName.equals(activity.getString(R.string.main_action_recycler_view))) {
                RecyclerViewActivity.launch(activity);
            } else if (actionName.equals(activity.getString(R.string.main_action_listview))) {
                ListViewActivity.launch(activity);
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
            } else if (actionName.equals(getString(R.string.main_action_marquee_view))) {
                MarqueeViewActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_lifecycle_owner_view))) {
                LifeCycleActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_scroll_rv))) {
                RvScrollActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_rxjava_anim))) {
                RxAnimActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_customize_view))) {
                CustomizeViewActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_transitiondrawable))) {
                TransitionDrawableActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_ui_touch_move))) {
                UITouchMoveActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_ui_touch_move_2))) {
                UITouchMove2Activity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_reflect_ioc))) {
                ReflectIocActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_eventbus_manual))) {
                EventBusOneActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_okhttp_manual))) {
                OkHttpActivity.launch(activity);
            } else if (actionName.equals(getString(R.string.main_action_glide_manual))) {
                GlideActivity.launch(activity);
            }
        }
    };

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, AndroidUIActivity.class);
        activity.startActivity(intent);
    }
}
