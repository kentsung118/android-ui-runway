package com.android.ui.kent.demo.recyclerview.scroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.recyclerview.multi_layer.CenterLayoutManger;
import com.android.ui.kent.demo.recyclerview.multi_layer.lookback.TextAdapter;
import com.android.ui.kent.demo.recyclerview.multi_layer.model.TextVO;
import com.android.ui.kent.demo.recyclerview.util.FocusableQuickRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sroll_recyclerview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        TextAdapter adapter = new TextAdapter(genData(100));
        mainRv.setLayoutManager(new CenterLayoutManger(this));
        mainRv.setAdapter(adapter);
        mainRv.setCanFocusOutHorizontal(false);
        mainRv.setCanBoundarySearchNext(false);

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


    }

    private List<TextVO> genData(int count) {
        List<TextVO> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new TextVO(i + "P"));
        }
        return list;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                if (mIsScrolling) {
                    Timber.d(">> mainRv is Scrolling...");
                    return true;
                }


                int pos = mainRv.getCurrentFocusPosition();
                Timber.d(">> pos = %s", pos);
                View view = mainRv.getLayoutManager().findViewByPosition(pos);
                if (view != null) {
                    float y = view.getY();
                    float height = view.getBottom() - view.getTop();
                    Timber.d(">> pos (t,height) = (%s,%s)", y, height);
//                    mScrollObserver.addObserver(mainRv);
//                    mIsScrolling = true;
//                    mainRv.smoothScrollBy(0, (int)height);
                    mScrollObserver.rxSmoothScroll(mainRv, 0, (int) height)
                            .subscribe(new Observer() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    mIsScrolling = true;
                                }

                                @Override
                                public void onNext(Object value) {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    mIsScrolling = false;
                                    mainRv.setFocus( mainRv.getCurrentFocusPosition()+1);
                                }
                            });

                }

            }
        }
        return super.dispatchKeyEvent(event);
    }


    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, RvScrollActivity.class);
        activity.startActivity(intent);
    }
}
