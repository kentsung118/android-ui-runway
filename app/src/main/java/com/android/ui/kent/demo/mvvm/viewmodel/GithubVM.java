package com.android.ui.kent.demo.mvvm.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.android.ui.kent.demo.mvvm.repo.GithubRepo;
import com.android.ui.kent.demo.network.retrofit.vo.Repo;

import java.util.List;

/**
 * Created by Kent Song on 2019/2/15.
 */
public class GithubVM extends AndroidViewModel {

    GithubRepo mGithubRepo;

    public GithubVM(@NonNull Application application) {
        super(application);
        mGithubRepo = new GithubRepo();
    }

    public void queryUserRepo(String account) {
        mGithubRepo.queryRepo(account);
    }

    public LiveData<Lcee<List<Repo>>> getRepoData() {
        return mGithubRepo.getRepoData();
    }
}
