package com.android.ui.kent.demo.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.listview.ListViewActivity;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kent on 2016/9/30.
 */

public class AlarmActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);

        initToolbar();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.enableBackButton();
        this.setToolbarTitle("AlarmManager");
    }

    @OnClick(R.id.alarm_icon)
    void onImageClick(){
        processSetAlarm();
    }

    // 設定提醒日期時間
    private void processSetAlarm() {
        Calendar calendar = Calendar.getInstance();

        final Item item = new Item();
//        if (item.getAlarmDatetime() != 0) {
//            // 設定為已經儲存的提醒日期時間
//            calendar.setTimeInMillis(item.getAlarmDatetime());
//        }

        // 讀取年、月、日、時、分
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // 儲存設定的提醒日期時間
        final Calendar alarm = Calendar.getInstance();

        // 設定提醒時間
        TimePickerDialog.OnTimeSetListener timeSetListener =
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        alarm.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        alarm.set(Calendar.MINUTE, minute);

                        item.setAlarmDateTime(alarm.getTimeInMillis());
                        settingAlarm(item);
                    }
                };

        // 選擇時間對話框
        final TimePickerDialog tpd = new TimePickerDialog(
                this, timeSetListener, hour, minute, true);

        // 設定提醒日期
        DatePickerDialog.OnDateSetListener dateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view,
                                          int year,
                                          int monthOfYear,
                                          int dayOfMonth) {
                        alarm.set(Calendar.YEAR, year);
                        alarm.set(Calendar.MONTH, monthOfYear);
                        alarm.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // 繼續選擇提醒時間
                        tpd.show();
                    }
                };

        // 建立與顯示選擇日期對話框
        final DatePickerDialog dpd = new DatePickerDialog(
                this, dateSetListener, year, month, day);
        dpd.show();


    }


    private void settingAlarm(Item item){
        // 設定提醒
        if (item.getAlarmDateTime() != 0) {
            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra("title", "發出廣播");

            PendingIntent pi = PendingIntent.getBroadcast(
                    this, 100,
                    intent, PendingIntent.FLAG_ONE_SHOT);

            AlarmManager am = (AlarmManager)
                    getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, item.getAlarmDateTime(), pi);
        }
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, AlarmActivity.class);
        activity.startActivity(intent);
    }
}
