package com.android.ui.kent.demo.network.rxjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.network.okhttp.OKHttpActivity;
import com.android.ui.kent.demo.network.retrofit.vo.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Kent on 2016/11/19.
 */

public class RxJavaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("RXJava");
        this.enableBackButton();
    }


    @OnClick(R.id.btn_repo)
    void onBtnRepoClick() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        Observable<User> userObservable = retrofit
                .create(GitHubUser.class)
                .getUser("kentsong")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


//        userObservable.subscribe(new Action1<User>() {
//            @Override
//            public void call(User user) {
//                Log.d("tester", "json = " + new Gson().toJson(user));
//
//            }
//        });

        Subscriber<User> mySubscriber = new Subscriber<User>() {
            @Override
            public void onNext(User user) {
                Log.d("tester", "json = " + new Gson().toJson(user));
            }

            @Override
            public void onCompleted() { System.out.println("onCompleted is called"); }

            @Override
            public void onError(Throwable e) { }
        };

        userObservable.subscribe(mySubscriber);


//
//        Observable<User> repoObservable = retrofit
//                .create(GitHubRepo.class)
//                .getRepo("kentsong")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread());

//
//        GitHubUser gitHubUser = retrofit.create(GitHubUser.class);
//        GitHubRepo gitHubRepo = retrofit.create(GitHubRepo.class);
//
//        Observable.zip(
//                gitHubUser.getUser("kentsong"),
//                gitHubRepo.getRepo("kentsong"),
//                new Func2< User , User , UserAndEvents >() {
//                    @Override
//                    public UserAndEvents  call (User user1 , User  user2 ) {
//                        Log.d("tester", "user1 = " + user1.id);
//                        Log.d("tester", "user2 = " + user2.id);
//                        return new UserAndEvents ( user1 ,  user2 );
//                    }
//                })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<UserAndEvents>() {
//                    @Override
//                    public void call(UserAndEvents userAndEvents) {
//                        Log.d("tester", "json = " + new Gson().toJson(userAndEvents));
//
//                    }
//                });





    }




    @OnClick(R.id.btn_user)
    void onBtnUserClick() {


    }


    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, RxJavaActivity.class);
        activity.startActivity(intent);
    }

}
