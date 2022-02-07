package com.kent.android.slim.sample

import android.content.Context
import android.util.Log
import java.io.File

/**
 * Created by Kent Sung on 2022/1/16.
 */
object Constants {

    var CACHE_OK_PATH: String? = null

    fun init(context: Context) {
        var cachePathDir: File
        Log.d("kent", "kent flag1 "+CACHE_OK_PATH);

        if (context.getFilesDir().also { cachePathDir = it } != null) {
            CACHE_OK_PATH = cachePathDir.toString() + "/okhttpclient/"
            Log.d("kent", "kent flag2 "+CACHE_OK_PATH);
        }
    }
}