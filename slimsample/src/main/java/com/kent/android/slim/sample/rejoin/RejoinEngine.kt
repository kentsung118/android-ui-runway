package com.kent.android.slim.sample.rejoin

import androidx.annotation.MainThread
import com.google.gson.Gson
import com.kent.android.slim.sample.Constants.init
import com.kent.android.slim.sample.rejoin.RejoinContract.featureMapping

/**
 * Create by Kent Sung on 2023/1/16.
 */

data class RejoinConfig(
    val lastStateData: String
)

class RejoinManager(val config: RejoinConfig) {
    // Disconnect 走 Remote
    // 正常開播走 local
    val remoteMap = mutableMapOf<String, RestoreEvent>() // from BE  用 Map 去解析 json
    val localMap = mutableMapOf<String, RestoreEvent>() // from mmkv

    // big model
    val remoteData = LastStreamingData(1, 1, "img_url")

    init {
        // parse remoteMap from RejoinConfig
        remoteMap[RejoinContract.FeatureKey.Camera.name] = RestoreEvent.CameraEvent(
            RejoinContract.FeatureKey.Camera.name,
            remoteData.camera
        )
//        remoteMap[RejoinContract.FeatureKey.CameraStatus.name] = RestoreEvent.CameraStatusEvent(
//            RejoinContract.FeatureKey.CameraStatus.name,
//            CameraMode(false, false)
//        )

        // parse localMap from localStoreMapping & mmkv
        featureMapping.forEach {
            //TODO should move to contract define
            val clss2 = when (it.value) {
                RestoreEvent.CameraStatusEvent::class.java -> RestoreEvent.CameraStatusEvent::class.java
                RestoreEvent.StreamingModeEvent::class.java -> RestoreEvent.StreamingModeEvent::class.java
                else -> {
                    null
                } // TODO throw Exception if not define in featureMapping
            }
            val gson = Gson()
            val json = "{\"key\":\"123\"}"
            val streamModeEvent = gson.fromJson(json, clss2)
            println(streamModeEvent.key)
            localMap.put(it.key.name, streamModeEvent)
        }

    }

    fun setRemoteConfig() {
        remoteMap["StreamMode"] = RestoreEvent.CameraStatusEvent("StreamMode", CameraMode(false, false))
    }

    // 可考慮更輕量的 function invoke 來 callback
    inline fun <reified T : RestoreEvent> initAction(key: String, cls: Class<T>, restoreAble: RestoreAble<T>) {
        val restoreState = true
        // realtime parse from local
        println("cls=$cls")

        if (restoreState) {
            restoreAble.restoreEvent(RestoreEvent.CameraStatusEvent(key, CameraMode(false, false)) as T)
        } else {
            val lastEvent = RestoreEvent.CameraStatusEvent(key, CameraMode(false, false))
            restoreAble.normalEvent(lastEvent as T)
        }
    }

    @MainThread
    inline fun <reified T : RestoreEvent> initFeature(key: RejoinContract.FeatureKey, restoreAble: RestoreAble<T>) {
        val cls = key.relatedClass<T>()
        val gson = Gson()
        println("cls2 -> ${cls}")
        val json = BackupStorage.get(key.name)
        if (json == null) {
            println("==> execute restoreAble.normalEvent,  but local config not found")
            restoreAble.normalEvent(null)

        } else {
//        val json = "{\"key\":\"123\",\"model\":{\"isFront\":true,\"isOpen\":true}}"
            val event = gson.fromJson(json, cls)
            println("result -> ${event.key}")
            println("==> execute restoreAble.normalEvent,  use local data")
            restoreAble.normalEvent(event)
        }


//        remoteMap.getOrElse(key.name){}

        if (remoteMap.containsKey(key.name)) {
            remoteMap[key.name]?.let {
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
        val lastEvent = RestoreEvent.CameraStatusEvent(key, CameraMode(false, false))
        val clas2 = cls::class.java
        restoreAble.normalEvent(lastEvent as T)
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



