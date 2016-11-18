package com.android.ui.kent.demo.network.retrofit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.network.retrofit.vo.Repo;
import com.android.ui.kent.demo.network.retrofit.vo.User;
import com.google.gson.Gson;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kent on 2016/11/18.
 */

public class RetrofitActivity extends BaseActivity implements Callback<List<Repo>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("Retrofit");
        this.enableBackButton();
    }


    @OnClick(R.id.btn_repo)
    void onBtnRepoClick(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<Repo>> repos = service.listRepos("kentsong");
        repos.enqueue(this);
//        repos.execute();

    }

    @OnClick(R.id.btn_user)
    void onBtnUserClick(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<User>> repos = service.listUser();
        repos.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Toast.makeText(RetrofitActivity.this, "onResponse json = " + new Gson().toJson(response), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
        Toast.makeText(this, "onResponse json = " + new Gson().toJson(response), Toast.LENGTH_LONG).show();
        List<Repo> list = response.body();
    }


    @Override
    public void onFailure(Call<List<Repo>> call, Throwable t) {
        Toast.makeText(this, "onFailure", Toast.LENGTH_SHORT).show();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, RetrofitActivity.class);
        activity.startActivity(intent);
    }
}
