package com.kent.android.slim.sample.tdd

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
}