package com.android.ui.kent.demo.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.android.ui.kent.demo.mvvm.repo.GithubRepo;
import com.android.ui.kent.demo.network.retrofit.vo.Repo;

import java.util.List;

/**
 * Created by Kent Song on 2019/2/15.
 */
public class GithubVM extends AndroidViewModel {

    GithubRepo mGithubRepo;

    LiveData<Throwable> mNetworkError;

    public GithubVM(@NonNull Application application) {
        super(application);
        mGithubRepo = new GithubRepo();
        mNetworkError = new MutableLiveData<>();
    }

    public void queryUserRepo(String account){
        mGithubRepo.queryRepo(account);
    }

    public LiveData<List<Repo>> getmRepoData() {
        return mGithubRepo.getmRepoData();
    }

    public LiveData<Throwable> getmNetworkError() {
        return mNetworkError;
    }
}
