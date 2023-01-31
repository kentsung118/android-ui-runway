package com.kent.android.slim.sample

import android.app.Application
import android.util.Log
import com.kent.android.slim.sample.service.TickHandler
import com.kent.android.slim.sample.service.TickSingleton
import kotlinx.coroutines.GlobalScope

/**
 * Created by Kent Sung on 2022/1/16.
 */
class MyApplication : Application () {
    override fun onCreate() {
        super.onCreate()
        Constants.init(this)

        Log.d("lala", "Application onCreate")
        TickSingleton.tickHandler = TickHandler(GlobalScope)
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d("lala", "Application onTerminate")
    }
}