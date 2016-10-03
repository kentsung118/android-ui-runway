package com.android.ui.kent.demo.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Kent on 2016/9/30.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 讀取記事標題
        String title = intent.getStringExtra("title");
        // 顯示訊息框
        Toast.makeText(context, title, Toast.LENGTH_LONG).show();
    }
}
