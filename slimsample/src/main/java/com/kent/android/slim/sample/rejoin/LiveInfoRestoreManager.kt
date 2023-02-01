package com.kent.android.slim.sample.rejoin

import androidx.annotation.MainThread
import com.google.gson.Gson

/**
 * Create by Kent Sung on 2023/1/16.
 */

data class RejoinConfig(
    val lastStateData: String
)

class LiveInfoRestoreManager(val config: RejoinConfig) {
    // Disconnect 走 Remote
    // 正常開播走 local
    val remoteMap = mutableMapOf<String, RestoreEvent>() // from BE  用 Map 去解析 json

    // big model from remote
    val remoteData = LastStreamingData(1, 1, "img_url")

    init {
        //TODO 拆分 hanler 去實作 parse feature
        // parse remoteMap from RejoinConfig
        remoteMap[LiveInfoRestoreContract.Feature.Camera.backendKey] = RestoreEvent.CameraEvent(
            remoteData.camera
        )
//        remoteMap[RejoinContract.FeatureKey.CameraStatus.name] = RestoreEvent.CameraStatusEvent(
//            RejoinContract.FeatureKey.CameraStatus.name,
//            CameraMode(false, false)
//        )

    }

    // 可考慮更輕量的 function invoke 來 callback
    inline fun <reified T : RestoreEvent> initAction(key: String, cls: Class<T>, restoreAble: RestoreAble<T>) {
        val restoreState = true
        // realtime parse from local
        println("cls=$cls")

        if (restoreState) {
//            restoreAble.restoreEvent(RestoreEvent.CameraStatusEvent(key, CameraMode(false, false)) as T)
        } else {
//            val lastEvent = RestoreEvent.CameraStatusEvent(key, CameraMode(false, false))
//            restoreAble.normalEvent(lastEvent as T)
        }
    }

    @MainThread
    inline fun <reified T : RestoreEvent> initFeature(feature: LiveInfoRestoreContract.Feature, restoreAble: RestoreAble<T>) {

        val cls = feature.relatedClass<T>()
        val gson = Gson()
        println("cls2 -> ${cls}")
        val json = BackupStorage.get(feature.name)
        if (json == null) {
            println("==> execute restoreAble.normalEvent,  but local config not found")
            restoreAble.normalEvent(null)

        } else {
            val event = gson.fromJson(json, cls)
            println("==> execute restoreAble.normalEvent,  use local data")
            restoreAble.normalEvent(event)
        }

        if (remoteMap.containsKey(feature.name)) {
            remoteMap[feature.backendKey]?.let {
                println("==> execute restoreAble.restoreEvent,  use remote data")
                restoreAble.restoreEvent(it as T)
            }
        } else {
            // use local if it exists
            json?.let {
                val event = gson.fromJson(json, cls)
                println("==> execute restoreAble.restoreEvent,  use local data")
                restoreAble.restoreEvent(event)
            }
        }
    }

    inline fun <reified T : RestoreEvent> initFeature(key: String, cls: T, restoreAble: RestoreAble<T>) {
//        val lastEvent = RestoreEvent.CameraStatusEvent(key, CameraMode(false, false))
//        val clas2 = cls::class.java
//        restoreAble.normalEvent(lastEvent as T)
    }


}

class RestoreConfigHelper() {

}


interface RestoreAble<T : RestoreEvent> {
    fun restoreEvent(lastModel: T?)
    fun normalEvent(lastModel: T?)
}

class FestureA : RestoreAble<RestoreEvent.CameraStatusEvent> {
    override fun restoreEvent(lastModel: RestoreEvent.CameraStatusEvent?) {
        TODO("Not yet implemented")
    }

    override fun normalEvent(lastModel: RestoreEvent.CameraStatusEvent?) {
        TODO("Not yet implemented")
    }
}



