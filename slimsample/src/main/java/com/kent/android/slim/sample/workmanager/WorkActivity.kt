package com.kent.android.slim.sample.workmanager

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.workmanager.Constants.TAG
import kotlinx.android.synthetic.main.activity_workmanager.*
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

/**
 * Created by songzhukai on 4/30/21.
 */
class WorkActivity : AppCompatActivity() {
    val work_tag = "kent_period_work"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workmanager)

        val workManager = WorkManager.getInstance(applicationContext)
        val uniqueWorkName = "單一事件"
        val uniqueWorkPeriodName = "週期事件"


        val singleRequest = OneTimeWorkRequest.Builder(MyWork::class.java)
                .addTag(work_tag)
                .build()
        Log.d(TAG, "singleRequest mId=${singleRequest.id}")

        Log.d(TAG, "onCreate")
        one_time_btn.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "click")
            submit(singleRequest)
        })

        delay_btn.setOnClickListener {
            Log.d(TAG, "click")
            val request = OneTimeWorkRequest.Builder(MyWork::class.java)
                    .setInitialDelay(30, TimeUnit.SECONDS)
                    .addTag(work_tag)
                    .build()
            submit(request)
        }


        period_btn.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "click")
            val request =
                    PeriodicWorkRequest.Builder(MyWork::class.java, 15, TimeUnit.MINUTES)
                            .addTag(work_tag)
                            // Additional configuration
                            .build()
            workManager.enqueueUniquePeriodicWork("週期事件", ExistingPeriodicWorkPolicy.KEEP, request)
        })

        query_btn.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "click")
            val workInfosByTag: ListenableFuture<List<WorkInfo>> = WorkManager.getInstance(applicationContext)
                    .getWorkInfosByTag(work_tag)

            Log.d(TAG, "worker size=${workInfosByTag.get().size}")
            Log.d(TAG, "worker =${workInfosByTag.get()}")
        })

        clear_btn.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "click")
//            workManager.cancelWorkById(syncWorker.id)
//            workManager.cancelUniqueWork("sync")
            WorkManager.getInstance(applicationContext).cancelAllWorkByTag(work_tag)
        })

        workManager.getWorkInfosForUniqueWorkLiveData(uniqueWorkPeriodName)
                .observe(this, Observer {
                    Log.d(TAG, "list.size=${it.size}")
                    for(info in it){
                        Log.d(TAG, "mId=${info.id}, state=${info.state}")
                    }
                })

        workManager.getWorkInfoByIdLiveData(singleRequest.id)
                .observe(this, Observer {
                    Log.d(TAG, "mId=${it.id}, state=${it.state}")
                })


    }

    fun submit(request: OneTimeWorkRequest) {
        WorkManager
                .getInstance(applicationContext)
                .enqueueUniqueWork("單一事件", ExistingWorkPolicy.KEEP, request)
    }
}

class MyWork(appContext: Context, workerParams: WorkerParameters) :
        Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.d(TAG, "Worker is doWork...")
        return Result.success()
    }
}