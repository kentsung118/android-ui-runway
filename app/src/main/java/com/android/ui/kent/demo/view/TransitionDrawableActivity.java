package com.android.ui.kent.demo.view;

import com.android.ui.kent.R;
import com.android.ui.kent.demo.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by songzhukai on 2019-10-15.
 */
public class TransitionDrawableActivity extends BaseActivity {

    @BindView(R.id.img)
    ImageView mImageView;

    @BindView(R.id.button)
    Button mButton;

    private int i;

    private int change = 0;//记录下标
    private int[] ids = new int[]{R.drawable.category1, R.drawable.category2, R.drawable.category3};
    private Drawable[] drawables;//图片集合
    private Thread mThread;//线程
    private boolean mThreadFlag = true;//线程结束标志符

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_drawable);
        ButterKnife.bind(this);

        initToolbar();
        initDrawableView();
        mThread = new Thread(new MyRunnable());
        mThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThreadFlag = false;//结束线程
    }

    private void initToolbar() {
        this.setupToolbar();
        this.setToolbarTitle(getString(R.string.main_action_transitiondrawable));
        this.enableBackButton();
    }

    private void initDrawableView() {
        //填充图片
        drawables = new Drawable[ids.length];
        for (int i = 0; i < ids.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawables[i] = getDrawable(ids[i]);
            } else {
                drawables[i] = getResources().getDrawable(ids[i]);
            }
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int duration = msg.arg1;
            TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{drawables[change % ids.length],
                    drawables[(change + 1) % ids.length]});
            change++;//改变标识位置
            mImageView.setBackground(transitionDrawable);
            transitionDrawable.startTransition(duration);
            return false;
        }
    });

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            //这个while(true)是做死循环
            while (mThreadFlag) {
                int duration = 5000;//改变的间隔
                Message message = mHandler.obtainMessage();
                message.arg1 = duration;
                mHandler.sendMessage(message);
                try {
                    Thread.sleep(duration);
                    //隔duration秒发送一次
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, TransitionDrawableActivity.class);
        activity.startActivity(intent);
    }
}
