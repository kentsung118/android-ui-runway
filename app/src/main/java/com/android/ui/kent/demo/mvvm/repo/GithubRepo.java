package com.android.ui.kent.demo.mvvm.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.android.ui.kent.demo.application.MyApplication;
import com.android.ui.kent.demo.mvvm.viewmodel.Lcee;
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
    final MediatorLiveData<Lcee<List<Repo>>> mRepoData = new MediatorLiveData<>();

    public GithubRepo() {
        MyApplication.getAppComponent().inject(this);
    }

    public void queryRepo(String account) {
        mRepoData.setValue(Lcee.loading());

        Call<List<Repo>> call = mGitHubService.listRepos(account);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repos = response.body();
                if (repos == null || repos.size() == 0) {
                    mRepoData.postValue(Lcee.empty());
                } else {
                    mRepoData.postValue(Lcee.content(repos));
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                mRepoData.postValue(Lcee.error(t));
            }
        });
    }

    public LiveData<Lcee<List<Repo>>> getRepoData() {
        return mRepoData;
    }

}
