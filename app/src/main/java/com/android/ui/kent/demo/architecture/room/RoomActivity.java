package com.android.ui.kent.demo.architecture.room;

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

import com.android.ui.kent.R;
import com.android.ui.kent.database.room.Member;
import com.android.ui.kent.database.room.MemberDao;
import com.android.ui.kent.database.room.MemberDb;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.application.MyApplication;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kent Song on 2019/2/11.
 */
public class RoomActivity extends BaseActivity {


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

    @Inject
    MemberDb mMemberDb;
    @Inject
    MemberDao mMemberDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);

        MyApplication.getAppComponent().inject(this);
    }

    @OnClick({R.id.btn_add, R.id.btn_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                Member member = new Member();
                member.setName("name1");
                member.setAge(18);
                member.setBirthday(new Date());

                Observable.just("")
                        .subscribeOn(Schedulers.io())
                        .doOnNext(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                mMemberDb.getMemberDao()
                                        .insert(member);
                            }
                        })
                        .doOnNext(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                queryMember();
                            }
                        })
                        .subscribe();

                break;
            case R.id.btn_del:

                if (TextUtils.isEmpty(editText.getText().toString())) {
                    return;
                }

                Observable.just("")
                        .subscribeOn(Schedulers.io())
                        .doOnNext(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Member member = mMemberDao.getMember(Integer.parseInt(editText.getText().toString()));
                                if (member != null) {
                                    mMemberDao.delete(member);
                                }
                            }
                        })
                        .doOnNext(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                queryMember();
                            }
                        })
                        .subscribe();
                break;
            default:
                break;
        }
    }

    private void queryMember() {
        List<Member> allMembers = MemberDb
                .getInstance(this)
                .getMemberDao()
                .getAllMembers();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                for (Member m : allMembers) {
                    sb.append(new Gson().toJson(m));
                    sb.append(System.lineSeparator());
                }
                textResult.setText(sb.toString());
            }
        });
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, RoomActivity.class);
        activity.startActivity(intent);
    }
}
