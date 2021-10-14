package com.kent.android.slim.sample.util

import androidx.annotation.AnyThread
import kotlinx.coroutines.*
import java.lang.Runnable

object CoroutineExecutor {
    val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @JvmStatic
    @AnyThread
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = coroutineScope.launch {
        block()
    }

    @JvmStatic
    @AnyThread
    fun launchIO(block: suspend CoroutineScope.() -> Unit) = coroutineScope.launch(Dispatchers.IO) {
        block()
    }

    @JvmStatic
    @AnyThread
    fun launchUIImmediate(block: suspend CoroutineScope.() -> Unit) = coroutineScope.launch(Dispatchers.Main.immediate) {
        block()
    }

    // Don't call Thread.sleep in your runnable
    @JvmStatic
    @AnyThread
    fun launchIO(runnable: Runnable) {
        launchIO {
            runnable.run()
        }
    }

    // Don't call Thread.sleep in your runnable
    @JvmStatic
    @AnyThread
    fun launchIO(runnable: Runnable, delay: Long) {
        launchIO {
            delay(delay)
            runnable.run()
        }
    }

    // Don't call Thread.sleep in your runnable
    @JvmStatic
    @AnyThread
    fun launchUI(runnable: Runnable) {
        launchUI {
            runnable.run()
        }
    }

    // Don't call Thread.sleep in your runnable
    @JvmStatic
    @AnyThread
    fun launchUIImmediate(runnable: Runnable) {
        launchUIImmediate {
            runnable.run()
        }
    }
}