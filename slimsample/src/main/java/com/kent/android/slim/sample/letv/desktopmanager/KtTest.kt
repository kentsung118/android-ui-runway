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

    val s2 = ::TextUtils
//    val s1 = :: SortHandler
//    s1(1,null,false).editDownSort()
    val foo = {x:Int, y:Int -> x+y}
    fun foo2(x:Int) = {y:Int -> x+y}

    println(foo(1,2))
    println(foo2(1).invoke(3))


    val w = {
        if(false){
            1
        }else {
            2
        }
    }

}