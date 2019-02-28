package com.android.ui.kent.demo.mvvm.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.ui.kent.demo.mvvm.repo.GithubRepo;
import com.android.ui.kent.demo.network.retrofit.vo.Repo;

import java.util.List;

/**
 * Created by Kent Song on 2019/2/15.
 */
public class GithubVM extends AndroidViewModel {

    GithubRepo mGithubRepo;

    MediatorLiveData<String> mRepoStatus = new MediatorLiveData<>();
//    SingleLiveEvent<String> mRepoStatus = new SingleLiveEvent<>();

    public GithubVM(@NonNull Application application) {
        super(application);
        mGithubRepo = new GithubRepo();
        setupObserveGithubRepo();
    }

    private void setupObserveGithubRepo() {
        mRepoStatus.addSource(mGithubRepo.getRepoData(), new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> repos) {
                mRepoStatus.postValue("請求成功");
            }
        });

        mRepoStatus.addSource(mGithubRepo.getmRepoError(), new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                mRepoStatus.postValue("what's up 發生錯誤啦!");
            }
        });


//        getRepoData().observeForever(new Observer<List<Repo>>() {
//            @Override
//            public void onChanged(@Nullable List<Repo> repos) {
//                mRepoStatus.postValue("請求成功");
//            }
//        });
//
//        mGithubRepo.getmRepoError().observeForever(new Observer<Throwable>() {
//            @Override
//            public void onChanged(@Nullable Throwable throwable) {
//                mRepoStatus.postValue("what's up 發生錯誤啦!");
//            }
//        });

    }

    public void queryUserRepo(String account) {
        mGithubRepo.queryRepo(account);
    }

    public LiveData<List<Repo>> getRepoData() {
        return mGithubRepo.getRepoData();
    }

    public LiveData<Throwable> getNetworkError() {
        return mGithubRepo.getmRepoError();
    }

    public MutableLiveData<String> getRepoStatus() {
        return mRepoStatus;
    }
}
