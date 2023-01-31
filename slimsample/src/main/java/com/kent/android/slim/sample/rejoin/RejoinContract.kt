package com.kent.android.slim.sample.rejoin

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kent.android.slim.sample.retorfit.RetrofitActivity
import kotlinx.android.parcel.Parcelize

/**
 * Created by Kent Sung on 2023/1/18.
 */
object RejoinContract {
    enum class FeatureKey(name: String, val clazz: Class<*>) {
        StreamMode("StreamMode", RestoreEvent.StreamingModeEvent::class.java),
        CameraStatus("CameraStatus", RestoreEvent.CameraStatusEvent::class.java),
        Camera("camera", RestoreEvent.CameraEvent::class.java);

        inline fun <reified T : RestoreEvent> relatedClass(): Class<T> {
            return clazz as Class<T>
        }

    }

    val featureMapping = mapOf<FeatureKey, Class<*>>(
        FeatureKey.StreamMode to RestoreEvent.StreamingModeEvent::class.java,
        FeatureKey.CameraStatus to RestoreEvent.CameraStatusEvent::class.java
    )
}

sealed class RestoreEvent(val key: String, val data: Any) {
    class CameraStatusEvent(key: String, val model: CameraMode) : RestoreEvent(key, model)
    class StreamingModeEvent(key: String, val model: StreamingMode) : RestoreEvent(key, model)
    class CameraEvent(key: String, val camera: Int) : RestoreEvent(key, camera) {
//        override val key = RejoinContract.FeatureKey.Camera.name
    }

//    abstract key
}

sealed class BackupModel : Parcelable {
    @Parcelize
    class CameraMode(
        @SerializedName("camera")
        val name: Int
    ) : BackupModel()
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