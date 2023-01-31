package com.kent.android.slim.sample.service

import android.os.SystemClock
import android.util.Log
import com.kent.android.slim.sample.Constants.init
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.filter

/**
 * Created by Kent Sung on 2022/11/29.
 */
// Class that centralizes when the content of the app needs to be refreshed
class TickHandler(
    private val externalScope: CoroutineScope,
    private val tickIntervalMs: Long = 2 * 1000
) {
    // Backing property to avoid flow emissions from other classes
    private val _tickFlow = MutableSharedFlow<Long>(replay = 0)
    val tickFlow: SharedFlow<Long> = _tickFlow
    val tickTimerFlow: Flow<String> =
        tickFlow.filter {
            System.currentTimeMillis() - intervalTime >= lastEmitTime
        }.map {
            lastEmitTime = System.currentTimeMillis()
            val date = Date(it)
            val result = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date)
            result
        }.onEach {
//            Log.d("lala", "tickTimerFlow emit -> $it")
        }
    val intervalTime = 5000
    private var lastEmitTime: Long = 0

    init {
        externalScope.launch {
            while (true) {
                val time = System.currentTimeMillis()
//                Log.d("lala", "TickHandler emit $time")
                _tickFlow.emit(time)
                delay(tickIntervalMs)
            }
        }
    }

}

object TickSingleton {
    var tickHandler: TickHandler? = null
}

