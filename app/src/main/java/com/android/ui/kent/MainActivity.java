package com.android.ui.kent;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.alarm.AlarmActivity;
import com.android.ui.kent.demo.drawer.DrawerActivity;
import com.android.ui.kent.demo.listview.ListViewActivity;
import com.android.ui.kent.demo.recyclerview.RecyclerViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.list_view)
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
                default:
                    break;
            }
        }
    };



}
