package com.android.ui.kent.demo.network.okhttp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Kent on 2016/11/19.
 */

public class OKHttpActivity extends BaseActivity {

    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("OKHttp");
        this.enableBackButton();
    }


    @OnClick(R.id.btn_repo)
    void onBtnRepoClick() {

        try {
            Call call = httpGet("https://api.github.com/users/kentsong");
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    final String json = response.body().string();
                    Log.d("OKHTTP", json);

                    OKHttpActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(OKHttpActivity.this, "onResponse json = " + json, Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





    @OnClick(R.id.btn_user)
    void onBtnUserClick() {

//        try {
//            String result = httpPost("https://api.github.com/users/kentsong/", "");
//            Toast.makeText(this, "onResponse json = " + result, Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        try {
            Call call = httpPost("https://api.github.com/users/kentsong","");
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    final String json = response.body().string();
                    Log.d("OKHTTP", json);

                    OKHttpActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(OKHttpActivity.this, "onResponse json = " + json, Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    private Call httpGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        return client.newCall(request);
    }

    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");


    private Call httpPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request);
    }


    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, OKHttpActivity.class);
        activity.startActivity(intent);
    }
}
