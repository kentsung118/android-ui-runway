package com.android.ui.kent.demo.reflect_ioc;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.reflect_ioc.anotation.ContentView;
import com.android.ui.kent.demo.reflect_ioc.anotation.InjectView;
import com.android.ui.kent.demo.reflect_ioc.anotation.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

@ContentView(R.layout.activity_reflect_ioc)
public class ReflectIocActivity extends BaseActivity {

    private String TAG = ReflectIocActivity.class.getSimpleName();

    @InjectView(R.id.button)
    Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, mButton.getText(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button)
    public void onIocButtonClick(View view){
        Toast.makeText(this, mButton.getText(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onIocButtonClick");
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ReflectIocActivity.class);
        activity.startActivity(intent);
    }
}
