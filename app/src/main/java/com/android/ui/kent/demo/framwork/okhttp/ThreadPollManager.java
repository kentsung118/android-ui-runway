package com.android.ui.kent.demo.framwork.okhttp;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by songzhukai on 2019-11-12.
 */
//线程池管理类
public class ThreadPollManager {

    private static String TAG = ThreadPollManager.class.getSimpleName();

    private static ThreadPollManager sThreadPollManager = new ThreadPollManager();

    public static ThreadPollManager getInstance() {
        return sThreadPollManager;
    }

    //创建存放网路请求队列
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    //将请求放入队列
    public void addTask(Runnable runnable) {
        try {
            mQueue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //创建线程池
    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadPollManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
                //当放满时...
                addTask(runnable);
//                if(httpTask.getRetryCount <=0){
//                   addTask(runnable);
//                }else {
//                   addDelayTask(runnable);
//                }

            }
        });
        mThreadPoolExecutor.execute(ddThread); /** 放入调度线程(叫号员) ( 持续找寻 mQueue 里面有没有任务 ) **/
        mThreadPoolExecutor.execute(delayThread); /** 放入错误重式机制线程 **/
    }

    //创建调度线程
    public Runnable ddThread = new Runnable() {
        Runnable runn = null;

        @Override
        public void run() {
            while (true) {
                try {
                    runn = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (runn != null) {

                    Log.e("JsonHttpRequest", "取得任務準備調度 runn =" + runn);
                    mThreadPoolExecutor.execute(runn);
                }
            }
        }
    };

    // 失败队列 (重复次数、间隔时间)
    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<HttpTask>();

    // 将失败的任务加到 DelayQueue
    public void addDelayTask(HttpTask t) {
        if (t != null) {
            if (t.isRetryCount()) {
                t.setDelayTime(3000);
                mDelayQueue.offer(t);
            }
        }
    }

    //延迟队列调度线程
    private Runnable delayThread = new Runnable() {
        @Override
        public void run() {
            HttpTask ht = null;
            while (true) {
                try {
                    ht = mDelayQueue.take();
                    if (ht.getRetryCount() < 3) {
                        mThreadPoolExecutor.execute(ht);
                        ht.setRetryCount(ht.getRetryCount() + 1);
                        String msg = "====重式机制====" + ht.getRetryCount();
                        Log.e(TAG, msg);
                    } else {
                        String msg = "====重式机制==== 总是失败，不处理了";
                        Log.e(TAG, msg);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
