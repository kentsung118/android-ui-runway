package com.android.ui.kent.demo.drawer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kent on 2016/10/3.
 */

public class DrawerActivity extends BaseActivity {

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.left_drawer_container)
    ViewGroup leftDrawerContainer;

    @Bind(R.id.side_listview)
    ListView sideItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);

        initToolbar();
        initNavigationDrawer();

    }

    private void initToolbar(){
        this.setupToolbar();
        this.setToolbarTitle("Navigation Drawer");

    }

    private void initNavigationDrawer(){
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        final String[] list = {"鉛筆","原子筆","鋼筆","毛筆","彩色筆"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        sideItems.setAdapter(arrayAdapter);
        sideItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你選擇的是" + list[position], Toast.LENGTH_SHORT).show();
            }
        });



    }

    @OnClick({R.id.btn_open, R.id.btn_close})
    public void onBtnClick(View view){
        if(view.getId() == R.id.btn_open){
            drawerLayout.openDrawer(leftDrawerContainer);
        } else{
            drawerLayout.closeDrawer(leftDrawerContainer);
        }
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, DrawerActivity.class);
        activity.startActivity(intent);
    }

}
