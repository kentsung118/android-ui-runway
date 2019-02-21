package com.android.ui.kent.demo.mvvm.repo;

import android.arch.lifecycle.MutableLiveData;

import com.android.ui.kent.demo.network.GitHubService;
import com.android.ui.kent.demo.network.GitHub_API;
import com.android.ui.kent.demo.network.retrofit.vo.Repo;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Kent Song on 2019/2/15.
 */
public class GithubRepo {

    private GitHubService mGitHubService;
    private MutableLiveData<List<Repo>> mRepoData;
    private MutableLiveData<Throwable> mRepoError;


    public GithubRepo() {
        mGitHubService = GitHub_API.getGithubService();
        mRepoData = new MutableLiveData<>();
        mRepoError = new MutableLiveData<>();
    }


    public void queryRepo(String account) {
        Call<List<Repo>> call = mGitHubService.listRepos(account);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repos = response.body();
                if(repos != null && repos.size() > 0){
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
