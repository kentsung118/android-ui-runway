package com.kent.android.slim.sample.rejoin

import com.google.gson.Gson


/**
 * Created by Kent Sung on 2023/1/18.
 */
object LiveInfoRestoreContract {
    enum class Feature(val backendKey: String, val clazz: Class<*>) {
        CameraStatus("camera_status", RestoreEvent.CameraStatusEvent::class.java),
        Camera("camera", RestoreEvent.CameraEvent::class.java),
        StreamMode("mode", RestoreEvent.StreamModeEvent::class.java),
        FeatureA("featureA", RestoreEvent.FeatureAEvent::class.java),

    }
}

sealed class RestoreEvent(val data: Any) {
    data class CameraStatusEvent(val model: CameraMode) : RestoreEvent(model) {
        override val releatedFeatrue: LiveInfoRestoreContract.Feature
            get() = LiveInfoRestoreContract.Feature.CameraStatus
    }

    data class CameraEvent(val camera: Int) : RestoreEvent(camera) {
        override val releatedFeatrue: LiveInfoRestoreContract.Feature
            get() = LiveInfoRestoreContract.Feature.Camera
    }

    data class StreamModeEvent(val mode: Int) : RestoreEvent(mode) {
        override val releatedFeatrue: LiveInfoRestoreContract.Feature
            get() = LiveInfoRestoreContract.Feature.StreamMode
    }

    data class FeatureAEvent(
        val data1: FeatureAData
    ) : RestoreEvent(data1) {
        override val releatedFeatrue: LiveInfoRestoreContract.Feature
            get() = LiveInfoRestoreContract.Feature.FeatureA
    }

    abstract val releatedFeatrue: LiveInfoRestoreContract.Feature // BE Key


//    abstract key
}

data class CameraMode(
    val isFront: Boolean,
    val isOpen: Boolean
)

data class StreamingMode(
    val mode: Int,
)

data class LastStreamingData(
    val stateCode: Int,
    val camera: Int,
    val backgroundURL: String
)

data class FeatureAData(
    val mode: Int,
    val name: String,
)