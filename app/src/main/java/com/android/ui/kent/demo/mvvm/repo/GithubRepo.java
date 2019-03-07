package com.android.ui.kent.demo.mvvm.repo;

import android.arch.lifecycle.MutableLiveData;

import com.android.ui.kent.demo.application.MyApplication;
import com.android.ui.kent.demo.network.GitHubService;
import com.android.ui.kent.demo.network.retrofit.vo.Repo;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kent Song on 2019/2/15.
 */
public class GithubRepo {

    @Inject
    GitHubService mGitHubService;
    private MutableLiveData<List<Repo>> mRepoData;
    private MutableLiveData<Throwable> mRepoError;


    public GithubRepo() {
        mRepoData = new MutableLiveData<>();
        mRepoError = new MutableLiveData<>();

        MyApplication.getAppComponent().inject(this);
    }


    public void queryRepo(String account) {
        Call<List<Repo>> call = mGitHubService.listRepos(account);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repos = response.body();
                if (repos != null && repos.size() > 0) {
                    mRepoData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                mRepoError.postValue(t);
            }
        });
    }

    public MutableLiveData<List<Repo>> getRepoData() {
        return mRepoData;
    }

    public MutableLiveData<Throwable> getmRepoError() {
        return mRepoError;
    }
}
