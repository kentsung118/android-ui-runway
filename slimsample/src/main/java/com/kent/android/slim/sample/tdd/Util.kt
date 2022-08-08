package com.kent.android.slim.sample.tdd

import java.lang.Exception

/**
 * Created by songzhukai on 21/01/2021.
 */
class Util {
    fun ok() {
        //static method
        UtilJava.ok()
        //static method kt
        UtilKotlin.ok()
        //static method kt
        UtilCompanion.ok()
        //singleton object kt
        UtilSingleton.ok()
        //一般 class
        Normal().ok()
    }

    fun compareVersion(current: String, target: String): Boolean {
        try {
            val currentArray = current.split(".")
            val targetArray = target.split(".")
            currentArray.forEachIndexed { index, str ->
                if (str.toInt() > targetArray[index].toInt()) {
                    return true
                }
                if (str.toInt() < targetArray[index].toInt()) {
                    return false
                }
            }
            return true
        } catch (e: Exception) {
            return false
        }

    }
}