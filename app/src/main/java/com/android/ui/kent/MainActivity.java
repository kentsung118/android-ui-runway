package com.android.ui.kent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.alarm.AlarmActivity;
import com.android.ui.kent.demo.cardview.CardActivity;
import com.android.ui.kent.demo.drawer.DrawerActivity;
import com.android.ui.kent.demo.eventbus.EventBusActivity;
import com.android.ui.kent.demo.indicator.IndicatorActivity;
import com.android.ui.kent.demo.listview.ListViewActivity;
import com.android.ui.kent.demo.network.okhttp.OKHttpActivity;
import com.android.ui.kent.demo.network.retrofit.RetrofitActivity;
import com.android.ui.kent.demo.network.rxjava.RxJavaActivity;
import com.android.ui.kent.demo.recyclerview.RecyclerViewActivity;
import com.android.ui.kent.demo.recyclerview.loadmore.LoadMoreActivity;
import com.android.ui.kent.demo.tab.TabActivity;
import com.android.ui.kent.demo.widget.dialog.DialogActivity;
import java.util.ArrayList;
import java.util.List;

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

    private void initToolbar(){
        this.setupToolbar();
        this.setToolbarTitle("UIRunway");
    }

    private void init(){

        List<String> list = new ArrayList<>();
        list.add("RecyclerView");
        list.add("ListView");
        list.add("Alarm Manager");
        list.add("Navigation Drawer");
        list.add("TabLayout + ViewPager");
        list.add("CirclePageIndicator + ViewPager");
        list.add("CardView");
        list.add("Retrofit");
        list.add("OKHttp");
        list.add("RxJava");
        list.add("EventBus");
        list.add("Dialog");
        list.add("LoadMore");

        ArrayAdapter<String> listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(onItemClickListener);

    }

    private ListView.OnItemClickListener onItemClickListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Activity activity = MainActivity.this;
            switch (position){
                case 0:
                    RecyclerViewActivity.launch(activity);
                    break;
                case 1:
                    ListViewActivity.launch(activity);
                    break;
                case 2:
                    AlarmActivity.launch(activity);
                    break;
                case 3:
                    DrawerActivity.launch(activity);
                    break;
                case 4:
                    TabActivity.launch(activity);
                    break;
                case 5:
                    IndicatorActivity.launch(activity);
                    break;
                case 6:
                    CardActivity.launch(activity);
                    break;
                case 7:
                    RetrofitActivity.launch(activity);
                    break;
                case 8:
                    OKHttpActivity.launch(activity);
                    break;
                case 9:
                    RxJavaActivity.launch(activity);
                    break;
                case 10:
                    EventBusActivity.launch(activity);
                    break;
                case 11:
                    DialogActivity.launch(activity);
                    break;
                case 12:
                    LoadMoreActivity.launch(activity);
                    break;
                default:
                    break;
            }
        }
    };



}
