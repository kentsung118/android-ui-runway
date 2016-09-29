package com.android.ui.kent.demo.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kent on 2016/9/27.
 */

public class RecyclerViewActivity extends BaseActivity implements BaseActivity.MenuOptionItemListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);
        this.setOptionItemListener(this);

        initToolbar();
        init();
    }

    private void initToolbar(){
        this.setupToolbar();
        this.enableBackButton();
        this.setToolbarTitle("RecyclerView");
    }


    private void init(){
        itemAdapter = new ItemAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);

        //設定action bar spinner
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        int[] image = { R.drawable.ic_view_list_white_24dp, R.drawable.ic_view_module_white_24dp, R.drawable.ic_dashboard_white_24dp};
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("icon", image[i]);
            items.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, items,
                R.layout.view_item_spinner_image, new String[]{"icon"}, new int[]{R.id.icon});

        spinner.setAdapter(simpleAdapter);
        spinner.setOnItemSelectedListener(onItemSelectedListener);
        return super.onCreateOptionsMenu(menu);
    }

    private Spinner.OnItemSelectedListener onItemSelectedListener = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this));
                    break;
                case 1:
                    recyclerView.setLayoutManager(new GridLayoutManager(RecyclerViewActivity.this, 2));
                    break;
                case 2:
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    public void onMenuOptionItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add){
            itemAdapter.addItem();
            itemAdapter.notifyDataSetChanged();
        } else {
            if(itemAdapter.getItemCount() == 0){
                return;
            }
            itemAdapter.removeItem();
            itemAdapter.notifyDataSetChanged();
        }
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, RecyclerViewActivity.class);
        activity.startActivity(intent);
    }
}
