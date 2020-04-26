package com.android.ui.kent.demo.framwork.retrofit;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.framwork.retrofit.annotation.Field;
import com.android.ui.kent.demo.framwork.retrofit.annotation.GET;
import com.android.ui.kent.demo.framwork.retrofit.annotation.POST;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.HttpUrl;



/**
 * @See RetrofitTest {@link RetrofitTest }
 * Run UnitTest
 * Created by songzhukai on 2019-11-15.
 */
public class RetrofitActivity extends BaseActivity {
    private String TAG = RetrofitActivity.class.getSimpleName();

    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.btn1)
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this);

        initToolbar();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle("EventBus");
        this.enableBackButton();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, RetrofitActivity.class);
        activity.startActivity(intent);
    }

    interface Host {

        @GET("/ip/ipNew")
        Call get(@Field("ip") String ip, @Field("key") String key);

        @POST("/ip/ipNew")
        Call post(@Field("ip") String ip, @Field("key") String key);
    }

    private final String BASE_URL = "http://apis.juhe.cn";
    private final String IP = "144.34.161.97";
    private final String KEY = "e8384b6fb117e7fa4faef010da01466a";


    @OnClick(R.id.btn1)
    public void onClick() {

        /** @See RetrofitTest {@link RetrofitTest}
         *  Run UnitTest
         */
        Retrofit retrofit = Retrofit.newBuilder()
                .setBaseUrl(HttpUrl.parse(BASE_URL))
                .build();

        Host host = retrofit.create(Host.class);

    }


}
