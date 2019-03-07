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
import com.android.ui.kent.demo.network.GitHubService;
import com.android.ui.kent.demo.network.GitHub_API;
import com.android.ui.kent.demo.network.retrofit.vo.Repo;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Kent Song on 2019/2/15.
 */
public class MvvmActivity extends BaseActivity {

    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.btn_query)
    Button btnQuery;
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

        Timber.d(">>  mGithubVM.getRepoStatus().observe");
        mGithubVM.getRepoStatus().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Timber.d(">>  mGithubVM.getRepoStatus().onChanged");
                Toast.makeText(MvvmActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });

    }

    @OnClick({R.id.btn_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                String account = editText.getText().toString();
//              mvvm mode
                mGithubVM.queryUserRepo(account);
//              basic mode
//                requestUserRepo(account);
                break;
        }
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MvvmActivity.class);
        activity.startActivity(intent);
    }

    private void requestUserRepo(String account){
        GitHubService mGitHubService = GitHub_API.getGithubService();
        Call<List<Repo>> call = mGitHubService.listRepos(account);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repos = response.body();
                if(repos != null && repos.size() > 0){
                    StringBuilder sb = new StringBuilder();
                    for (Repo repo : repos) {
                        sb.append(new Gson().toJson(repo)).append(System.lineSeparator());
                    }
                    textResult.setText(sb.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(MvvmActivity.this, "request 出错了", Toast.LENGTH_LONG).show();
            }
        });
    }
}
