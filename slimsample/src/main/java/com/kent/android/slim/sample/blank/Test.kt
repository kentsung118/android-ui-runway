package com.kent.android.slim.sample.blank

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Kent Sung on 2022/9/12.
 */
class Test {


}

val stateFlow = MutableStateFlow<String>("Hello")
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main() {

//    GlobalScope.launch {


        stateFlow.collect {
            println(it)
        }
        stateFlow.value = "X"
        stateFlow.value = "Y"
        stateFlow.value = "Z"
//        delay(500)
//    }
    Thread.sleep(1000)

}