package com.android.ui.kent.demo.mvvm.di;

import com.android.ui.kent.demo.architecture.room.RoomActivity;
import com.android.ui.kent.demo.mvvm.repo.GithubRepo;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kent Song on 2019/3/7.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(GithubRepo githubRepo);
    void inject(RoomActivity roomActivity);
}
