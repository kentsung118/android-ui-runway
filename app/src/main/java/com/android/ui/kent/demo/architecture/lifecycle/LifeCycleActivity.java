package com.android.ui.kent.demo.architecture.lifecycle;

import android.app.Activity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Kent Song on 2018/12/30.
 * 測試該 Activity 內部View 是否能感知外部 Activity Life cycle 變化
 */
public class LifeCycleActivity extends BaseActivity implements LifecycleOwner {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);
        ButterKnife.bind(this);

        initToolbar();
        ActTextView tv1 = new ActTextView(this);
        tv1.setText("我是Activity內部TextView，請看 log 生命週期情況");
        getLifecycle().addObserver(tv1);

        ConstraintLayout layout = findViewById(R.id.root_layout);
        layout.addView(tv1);

        KentFragment fragment = (KentFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_lifecycler_container);
        if (fragment == null) {
            fragment = new KentFragment();
            addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_lifecycler_container);
        } else {
            addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_lifecycler_container);

        }

    }

    public static void addFragmentToActivity(
            @NonNull FragmentManager fragmentManager,
            @NonNull Fragment fragment,
            int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commitAllowingStateLoss();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle(getString(R.string.main_action_lifecycle_owner_view));
        this.enableBackButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, LifeCycleActivity.class);
        activity.startActivity(intent);
    }
}
