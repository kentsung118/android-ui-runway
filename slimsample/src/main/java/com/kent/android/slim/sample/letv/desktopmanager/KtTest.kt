package com.kent.android.slim.sample.letv.desktopmanager

/**
 * Created by songzhukai on 2020/12/31.
 */
class KtTest {
}


fun main() {
    println("---main start---")

    listOf(1, 2, 3, 4, 5).forEach { num ->
        if (num < 3) {
            println(num)
            return@forEach
        }else {

            println("$num...")
        }

    }
    println("---main end---")
}