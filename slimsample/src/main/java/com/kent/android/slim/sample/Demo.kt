package com.kent.android.slim.sample

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.RuntimeException

/**
 * Created by Kent Sung on 2022/8/10.
 */
class Demo {
}

fun main() {
    println("flag0")
    runCatching {
        throw RuntimeException("kent test2")
    }.onFailure {
        println("flag0.2")
    }
    println("flag0.1")
    GlobalScope.launch {
        println("flag1")
//        throw RuntimeException("kent test")
//        val s = constructFromUrl()
        println("flag2")

//        println(s)
    }


}

suspend fun constructFromUrl(): Result<Nothing> =
    kotlin.runCatching {
        withContext(Dispatchers.IO) {
            throw RuntimeException("kent test")
        }
    }
