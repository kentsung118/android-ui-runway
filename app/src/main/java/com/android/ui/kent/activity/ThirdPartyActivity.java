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
import com.android.ui.kent.demo.blank.BlankActivity;
import com.android.ui.kent.demo.twitter.TwitterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kent Song on 2019/4/14.
 */
public class ThirdPartyActivity extends BaseActivity {
    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_party_view);
        ButterKnife.bind(this);

        initToolbar();
        init();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("Android Third Party");
    }

    private void init() {

        List<String> list = new ArrayList<>();
        list.add(getString(R.string.main_third_party_twitter));
        list.add(getString(R.string.main_third_party_block));

        ArrayAdapter<String> listAdapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(onItemClickListener);
    }

    private ListView.OnItemClickListener onItemClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Activity activity = ThirdPartyActivity.this;

            final String actionName = ((TextView) view).getText().toString();
            if (actionName.equals(activity.getString(R.string.main_third_party_twitter))) {
                TwitterActivity.launch(activity);
            }
            else if (actionName.equals(activity.getString(R.string.main_third_party_block))) {
                BlankActivity.launch(activity);
            }
        }
    };

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ThirdPartyActivity.class);
        activity.startActivity(intent);
    }
}
