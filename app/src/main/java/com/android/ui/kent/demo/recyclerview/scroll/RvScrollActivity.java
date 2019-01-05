package com.android.ui.kent.demo.recyclerview.scroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.view.KeyEvent;
import android.view.View;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.common.StartSnapHelper;
import com.android.ui.kent.demo.recyclerview.multi_layer.CenterLayoutManger;
import com.android.ui.kent.demo.recyclerview.multi_layer.lookback.TextAdapter;
import com.android.ui.kent.demo.recyclerview.multi_layer.model.TextVO;
import com.android.ui.kent.demo.recyclerview.util.FocusableQuickRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by Kent Song on 2019/1/5.
 */
public class RvScrollActivity extends BaseActivity {

    @BindView(R.id.main_rv)
    FocusableQuickRecyclerView mainRv;

    ScrollObserver mScrollObserver = new ScrollObserver();
    private boolean mIsScrolling;
    Integer mHeight;
    SnapHelper mSnapHelperCenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sroll_recyclerview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        TextAdapter adapter = new TextAdapter(genData(50));
        mainRv.setLayoutManager(new CenterLayoutManger(this, 1000));
        mainRv.setAdapter(adapter);
        mainRv.setCanFocusOutHorizontal(false);
        mainRv.setCanBoundarySearchNext(false);
        mScrollObserver.bindRv(mainRv);
//        mScrollObserver.setListener(new ScrollObserver.OnStateListener() {
//            @Override
//            public void onIdle() {
//                Timber.d(">> onIdle");
//                mIsScrolling = false;
//                mScrollObserver.rmObserver(mainRv);
//
//                mainRv.setFocus( mainRv.getCurrentFocusPosition()+1);
//            }
//        });


        SnapHelper snapHelperStart = new StartSnapHelper();
//        snapHelperStart.attachToRecyclerView(mainRv);
        mSnapHelperCenter = new LinearSnapHelper();
        mSnapHelperCenter.attachToRecyclerView(mainRv);


    }

    @Override
    protected void onResume() {
        super.onResume();
//        View view = mainRv.getLayoutManager().findViewByPosition(0);
//        Timber.d(">> view = %s", view);
//        mHeight = view.getBottom() - view.getTop();
    }

    private List<TextVO> genData(int count) {
        List<TextVO> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new TextVO(i + "P"));
        }
        return list;
    }

    private Disposable mDisposable;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT
                    || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {

                Timber.d(">> dispatchKeyEvent KeyEvent.KEYCODE RIGHT or LEFT");
//                return true;
                if (mHeight == null) {
                    int pos = mainRv.getCurrentFocusPosition();
                    Timber.d(">> pos = %s", pos);
                    View view = mainRv.getLayoutManager().findViewByPosition(pos);

                    if (view != null) {
                        float y = view.getY();
                        mHeight = view.getBottom() - view.getTop();
                    }

                    if (mHeight == null) {
                        return true;
                    }
                }

//                Timber.d(">> pos (t,height) = (%s,%s)", 0, mHeight);
                int y = event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT ? mHeight : -mHeight;

                if (mDisposable != null) {
                    mDisposable.dispose();
                }

                Timber.d(">> onComplete");
                int firstPos = ((LinearLayoutManager) mainRv.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                int lastPos = ((LinearLayoutManager) mainRv.getLayoutManager()).findLastVisibleItemPosition();
                int count = mainRv.getAdapter().getItemCount();
                if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT && lastPos == count - 1) {
                    int targetPos;
                    if (mainRv.getCurrentFocusPosition() < count - 4) {
                        targetPos = count - 4;
                    } else {
                        targetPos = mainRv.getCurrentFocusPosition() + 1;
                    }
                    mainRv.setFocus(targetPos);
                    return true;
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT && firstPos == 0) {
                    int targetPos;
                    if (mainRv.getCurrentFocusPosition() > 4) {
                        targetPos = 4;
                    } else {
                        targetPos = mainRv.getCurrentFocusPosition() - 1;
                    }
                    mainRv.setFocus(targetPos);
                    return true;
                }


                mScrollObserver.rxSmoothScroll(mainRv, 0, y)
//                        .delay(300, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                mDisposable = d;
                            }

                            @Override
                            public void onNext(Object value) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                mSnapHelperCenter.findSnapView(mainRv.getLayoutManager()).requestFocus();
                            }
                        });

//                }

            }
        }
        return super.dispatchKeyEvent(event);
    }


    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, RvScrollActivity.class);
        activity.startActivity(intent);
    }
}
