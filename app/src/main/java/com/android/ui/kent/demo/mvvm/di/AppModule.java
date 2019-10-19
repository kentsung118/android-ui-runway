package com.android.ui.kent.demo.mvvm.di;

import android.content.Context;

import com.android.ui.kent.database.room.MemberDao;
import com.android.ui.kent.database.room.MemberDb;
import com.android.ui.kent.demo.network.GitHubService;
import com.android.ui.kent.demo.network.GitHub_API;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kent Song on 2019/3/7.
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public GitHubService provideGitHubService() {
        return GitHub_API.getGithubService();
    }

    @Provides
    @Singleton
    public MemberDb provideMemberDb(){
        return MemberDb.getInstance(context);
    }

    @Provides
    @Singleton
    public MemberDao provideMemberDao(MemberDb memberDb){
        return memberDb.getMemberDao();
    }
}
