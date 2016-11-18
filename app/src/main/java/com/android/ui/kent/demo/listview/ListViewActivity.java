package com.android.ui.kent.demo.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kent on 2016/9/28.
 */

public class ListViewActivity extends BaseActivity{

    @BindView(R.id.list_view)
    ListView listView;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ButterKnife.bind(this);

        initToolbar();
//        sample1();
        sample2();
    }

    private void initToolbar(){
        this.setupToolbar();
        this.setToolbarTitle("ListView");
        this.enableBackButton();
    }

    private void sample1(){
        final String[] list = {"鉛筆","原子筆","鋼筆","毛筆","彩色筆"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你選擇的是" + list[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sample2(){
        final String[] strA = {"鉛筆","原子筆","鋼筆","毛筆","彩色筆"};
        final String[] strB = {"sub鉛筆","sub原子筆","sub鋼筆","sub毛筆","sub彩色筆"};

        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < strA.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("tt1", strA[i]);
            map.put("tt2", strB[i]);
            items.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, items,
                android.R.layout.simple_list_item_2, new String[]{"tt1", "tt2"}, new int[]{android.R.id.text1, android.R.id.text2});

        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你選擇的是" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sample3(){
        final String[] list = {"鉛筆","原子筆","鋼筆","毛筆","彩色筆"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_single_choice,list);
        listView.setAdapter(arrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你選擇的是" + list[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sample4(){
        final String[] list = {"鉛筆","原子筆","鋼筆","毛筆","彩色筆"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_checked,list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你選擇的是" + list[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sample5(){
        final String[] list = {"鉛筆","原子筆","鋼筆","毛筆","彩色筆"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_multiple_choice,list);
        listView.setAdapter(arrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你選擇的是" + list[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sample6(){
        final String[] list = {"鉛筆","原子筆","鋼筆","毛筆","彩色筆"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_activated_1,list);
        listView.setAdapter(arrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你選擇的是" + list[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sample7(){
        final String[] strA = {"鉛筆","原子筆","鋼筆","毛筆","彩色筆"};
        final String[] strB = {"sub鉛筆","sub原子筆","sub鋼筆","sub毛筆","sub彩色筆"};

        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < strA.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("tt1", strA[i]);
            map.put("tt2", strB[i]);
            items.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, items,
                android.R.layout.simple_list_item_activated_2, new String[]{"tt1", "tt2"}, new int[]{android.R.id.text1, android.R.id.text2});

        listView.setAdapter(simpleAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你選擇的是" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_spinner_single, menu);

        //設定action bar spinner
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        final String[] list = {"simple_list_item_1",
                "simple_list_item_2",
                "simple_list_item_single_choice",
                "simple_list_item_checked",
                "simple_list_item_multiple_choice",
                "simple_list_item_activated_1",
                "simple_list_item_activated_2"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(onItemSelectedListener);
        return super.onCreateOptionsMenu(menu);
    }



    private Spinner.OnItemSelectedListener onItemSelectedListener = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    sample1();
                    break;
                case 1:
                    sample2();
                    break;
                case 2:
                    sample3();
                    break;
                case 3:
                    sample4();
                    break;
                case 4:
                    sample5();
                    break;
                case 5:
                    sample6();
                    break;
                case 6:
                    sample7();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ListViewActivity.class);
        activity.startActivity(intent);
    }

}
