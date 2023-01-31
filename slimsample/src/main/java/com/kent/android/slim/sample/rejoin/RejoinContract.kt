package com.kent.android.slim.sample.rejoin


/**
 * Created by Kent Sung on 2023/1/18.
 */
object RejoinContract {
    enum class Features(val key: String, val clazz: Class<*>) {
        CameraStatus("CameraStatus", RestoreEvent.CameraStatusEvent::class.java),
        Camera("camera", RestoreEvent.CameraEvent::class.java);

        inline fun <reified T : RestoreEvent> relatedClass(): Class<T> {
            return clazz as Class<T>
        }

        fun getFeatureKey(): String {
            return name
        }

    }

}

sealed class RestoreEvent(val key: String, val data: Any) {
    class CameraStatusEvent(key: String, val model: CameraMode) : RestoreEvent(key, model){
        override val backendKey: String
            get() = "camera_status"
    }
    class CameraEvent(key: String, val camera: Int) : RestoreEvent(key, camera) {
        override val backendKey: String
            get() = "camera"
    }

    abstract val backendKey: String // BE Key


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