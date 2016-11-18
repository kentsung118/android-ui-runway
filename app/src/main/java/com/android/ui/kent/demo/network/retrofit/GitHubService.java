package com.android.ui.kent.demo.network.retrofit;

import com.android.ui.kent.demo.network.retrofit.vo.Repo;
import com.android.ui.kent.demo.network.retrofit.vo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Kent on 2016/11/18.
 */

public interface GitHubService {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("users")
    Call<List<User>> listUser();
}
