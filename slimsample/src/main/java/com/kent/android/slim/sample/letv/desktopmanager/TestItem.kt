package com.kent.android.slim.sample.letv.desktopmanager

import com.kent.android.slim.sample.letv.desktopmanager.bean.ScreenInfo
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class TestItem {
    /**
     * 位置向上移动
     * 检查锁，并且和锁前一位交换位置
     * **/
    fun editUpSort(itemList: ArrayList<ScreenInfo>, from: Int, to: Int): ArrayList<ScreenInfo> {

        println("排序前：$itemList")
        val locks = ArrayList<Int>()
        var moveNum = 0

        // step1.先记录锁的位置
        for ((index, item) in itemList.withIndex()) {
            if(index < to || index > from){
                continue
            }
            if (item.locked) {
                locks.add(index)
            }
        }

        val removedItem = itemList.removeAt(from)
        itemList.add(to, removedItem)
        println("排序(1)：$itemList")

        // step2.将记录位置和后一位交换
        for (from in locks) {
            itemList[from]
            val removedItem = itemList.removeAt(from)
            val to = from + 1
            itemList.add(to, removedItem)
            moveNum++
            println("moveItem from:$from ,to:$to")
        }

        println("排序(2)：$itemList")
        println("moveItem 次数：$moveNum")
        return itemList
    }

    /**
     * 位置向下移动
     * 检查锁，并且和锁后一位交换位置
     * **/
    fun editDownSort(itemList: ArrayList<ScreenInfo>, from: Int, to: Int): ArrayList<ScreenInfo> {

        println("排序前：$itemList")

        val locks = LinkedList<Int>()
        var moveNum = 0

        // step1.先记录锁的位置
        for ((index, item) in itemList.withIndex()) {
            if(index < from || index > to){
                continue
            }
            if (item.locked) {
                locks.add(index)
            }
        }



        val removedItem = itemList.removeAt(from)
        itemList.add(to, removedItem)
        println("排序(1)：$itemList")

        // step2.将记录位置和后一位交换
        while (locks.size != 0) {
            val from = locks.removeLast()
            val removedItem = itemList.removeAt(from)
            val to = from - 1
            itemList.add(to, removedItem)
            moveNum++
            println("moveItem from:$from ,to:$to")
        }



        println("排序(2)：$itemList")
        println("moveItem 次数：$moveNum")
        return itemList
    }
}



