package com.kent.android.slim.sample

import android.app.Application

/**
 * Created by Kent Sung on 2022/1/16.
 */
class MyApplication : Application () {
    override fun onCreate() {
        super.onCreate()
        Constants.init(this)
    }
}