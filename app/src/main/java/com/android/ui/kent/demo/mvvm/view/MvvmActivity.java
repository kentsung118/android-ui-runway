package com.android.ui.kent.demo.mvvm.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.mvvm.viewmodel.GithubVM;
import com.android.ui.kent.demo.network.retrofit.vo.Repo;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kent Song on 2019/2/15.
 */
public class MvvmActivity extends BaseActivity {

    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.btn_query)
    Button btnQuery;
//    @BindView(R.id.btn_api_error)
//    Button btnApiError;
    @BindView(R.id.text_result)
    TextView textResult;

    GithubVM mGithubVM;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);
        ButterKnife.bind(this);
        setupToolBar();

        mGithubVM = ViewModelProviders.of(this).get(GithubVM.class);
        setupObserve();
    }

    private void setupToolBar() {
        this.setupToolbar();
        this.setToolbarTitle(getString(R.string.main_action_mvvm));
    }

    private void setupObserve() {
        mGithubVM.getRepoData().observe(this, new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> repos) {
                StringBuilder sb = new StringBuilder();
                for (Repo repo : repos) {
                    sb.append(new Gson().toJson(repo)).append(System.lineSeparator());
                }
                textResult.setText(sb.toString());
            }
        });

        mGithubVM.getNetworkError().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                textResult.setText(throwable.getMessage());
            }
        });

        mGithubVM.getRepoStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(MvvmActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });

    }

    @OnClick({R.id.btn_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                String account = editText.getText().toString();
                mGithubVM.queryUserRepo(account);
                break;
        }
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MvvmActivity.class);
        activity.startActivity(intent);
    }
}
