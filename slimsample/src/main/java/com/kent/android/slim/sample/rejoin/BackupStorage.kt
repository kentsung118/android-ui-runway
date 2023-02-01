package com.kent.android.slim.sample.rejoin

import com.google.gson.Gson

/**
 * Created by Kent Sung on 2023/1/31.
 */
object BackupStorage {
    private val storeMap = mutableMapOf<String, String>()

    fun save(event:RestoreEvent) {
        val json = Gson().toJson(event)
        println("lala, BackupStorage save json=$json")
        storeMap[event.releatedFeatrue.name] = json
    }

    fun get(key: String): String? {
        return storeMap[key]
    }
}