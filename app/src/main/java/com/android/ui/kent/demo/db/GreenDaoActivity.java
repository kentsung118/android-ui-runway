package com.android.ui.kent.demo.db;

import com.android.ui.kent.R;
import com.android.ui.kent.database.greendao.DaoSession;
import com.android.ui.kent.database.greendao.User;
import com.android.ui.kent.database.greendao.UserDao;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.application.MyApplication;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by songzhukai on 2019-08-23.
 */
public class GreenDaoActivity extends BaseActivity {

    @BindView(R.id.toolbar_layout)
    AppBarLayout toolbarLayout;
    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_del)
    Button btnDel;
    @BindView(R.id.text_result)
    TextView textResult;
    @BindView(R.id.edit_name)
    EditText editName;

    private UserDao mUserDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        ButterKnife.bind(this);
        setupToolbar();
        setToolbarTitle(getString(R.string.main_action_greendao));

        DaoSession daoSession =  MyApplication.getDaoSession();
        mUserDao = daoSession.getUserDao();

    }

    @OnClick({R.id.btn_add, R.id.btn_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                User user = new User();
                user.setName(editName.getText().toString());
                user.setAge(10);

                mUserDao.insert(user);
                queryAllUser();
                break;
            case R.id.btn_del:
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    return;
                }
                User user1 = mUserDao.queryBuilder().where(UserDao.Properties._id.eq(Integer.parseInt(editText.getText().toString()))).unique();
                if(user1!= null){
                    mUserDao.delete(user1);
                }
                queryAllUser();
                break;
            default:
                break;
        }
    }

    private void queryAllUser() {
        List<User> list = mUserDao.queryBuilder().list();


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                for (User m : list) {
                    sb.append(new Gson().toJson(m));
                    sb.append(System.lineSeparator());
                }
                textResult.setText(sb.toString());
            }
        });
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, GreenDaoActivity.class);
        activity.startActivity(intent);
    }
}
