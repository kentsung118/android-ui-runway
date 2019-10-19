package com.android.ui.kent.demo.recyclerview.scroll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SnapHelper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;
import com.android.ui.kent.demo.recyclerview.multi_layer.CenterLayoutManger;
import com.android.ui.kent.demo.recyclerview.multi_layer.lookback.TextAdapter;
import com.android.ui.kent.demo.recyclerview.multi_layer.model.TextVO;
import com.android.ui.kent.demo.recyclerview.util.FocusableQuickRecyclerView;
import com.android.ui.kent.demo.recyclerview.util.ScrollFirstRecyclerView;
import com.android.ui.kent.demo.recyclerview.util.SimpleData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * Created by Kent Song on 2019/1/5.
 */
public class RvScrollActivity extends BaseActivity {

    @BindView(R.id.main_rv)
    FocusableQuickRecyclerView mainRv;

    @BindView(R.id.main_rv2)
    ScrollFirstRecyclerView mainRv2;

    ScrollObserver mScrollObserver = new ScrollObserver();
    private boolean mIsScrolling;
    Integer mHeight;
    SnapHelper mSnapHelperCenter;

    @BindView(R.id.btnTop)
    Button mButtonTop;
    @BindView(R.id.btnBottom)
    Button mButtonBottom;
    @BindView(R.id.btnLeft)
    Button mButtonLeft;
    @BindView(R.id.btnRight)
    Button mButtonRight;

    private boolean mActionUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sroll_recyclerview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        TextAdapter adapter = new TextAdapter(genData(50));
        Text2Adapter adapter2 = new Text2Adapter(genSimpleData(50));

        mainRv.getChildLayoutPosition(null);
        mainRv.getChildAdapterPosition(null);

        mainRv.setLayoutManager(new CenterLayoutManger(this, 1000));
        mainRv.setAdapter(adapter);
        mainRv.setCanFocusOutHorizontal(true);
        mainRv.setCanBoundarySearchNext(false);
        mainRv.setDebugMode(true);
        mainRv.enableRxScrollVertical();
        mainRv.setGainFocusChangeDescendant(true);


        mainRv2.setLayoutManager(new CenterLayoutManger(this, 1000));
        mainRv2.setAdapter(adapter2);
        mainRv2.setCanFocusOutHorizontal(true);
        mainRv2.enableRxScrollVertical();
//        mainRv2.setCanBoundarySearchNext(false);
//        mainRv2.setDebugMode(true);
//        mainRv2.enableRxScrollVertical();
//        mainRv2.setGainFocusChangeDescendant(true);

//        mScrollObserver.bindRv(mainRv);
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


//        SnapHelper snapHelperStart = new StartSnapHelper();
//        snapHelperStart.attachToRecyclerView(mainRv);
//        mSnapHelperCenter = new LinearSnapHelper();
//        mSnapHelperCenter.attachToRecyclerView(mainRv);

        mButtonTop.setOnFocusChangeListener(mFocusListener);
        mButtonBottom.setOnFocusChangeListener(mFocusListener);
        mButtonLeft.setOnFocusChangeListener(mFocusListener);
        mButtonRight.setOnFocusChangeListener(mFocusListener);


    }

    private View.OnFocusChangeListener mFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                ((Button) v).setText("获得焦点");
            } else {
                ((Button) v).setText("未获焦");

            }
        }
    };


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

    private List<SimpleData<TextVO>> genSimpleData(int count) {
        List<TextVO> list = genData(count);
        List<SimpleData<TextVO>> sList = new ArrayList<>();
        for (TextVO vo : list) {
            sList.add(new SimpleData<>(vo));
        }
        return sList;
    }

    private Disposable mDisposable;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        return super.dispatchKeyEvent(event);
    }


    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, RvScrollActivity.class);
        activity.startActivity(intent);
    }
}
