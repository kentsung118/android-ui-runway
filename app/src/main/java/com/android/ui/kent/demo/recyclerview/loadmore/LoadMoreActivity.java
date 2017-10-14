package com.android.ui.kent.demo.recyclerview.loadmore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.ui.kent.R;
import com.android.ui.kent.database.user.IUserDAO;
import com.android.ui.kent.database.user.UserDAO;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.model.database.vo.UserVO;
import java.util.List;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by Kent on 2017/10/14.
 */

public class LoadMoreActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private String TAG = LoadMoreActivity.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    IUserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        initToolbar();
        init();
    }

    private void initToolbar() {
        this.setupToolbar();
        this.enableBackButton();
        this.setToolbarTitle("RecyclerView LoadMore");
    }

    private void init() {
        userDAO = new UserDAO(this);

        //清空DB
        userDAO.clearAll();
        //新增20筆資料
        userDAO.addRandomUser(20);

        //先 show 10筆
        List<UserVO> userList = userDAO.getUserByQuery(new UserDAO.UserQuery());

        //下拉刷新SwipeRefresh
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );
        mSwipeRefreshLayout.setOnRefreshListener(this);


        //初始化recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.addOnScrollListener(new RecyclerOnScrollListener(linearLayoutManager, new RecyclerOnScrollListener.LoadMoreData() {
            @Override
            public void loadMore() {
                //下滑加載時，此method 會多次呼叫，需注意此問題
                Log.d(TAG, "onLoadMore");
            }
        }));

        UserAdapter userAdapter = new UserAdapter(this, userList);
        recyclerView.setAdapter(userAdapter);
    }

    //頂端下滑refresh
    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh");
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }





    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, LoadMoreActivity.class);
        activity.startActivity(intent);
    }



}
