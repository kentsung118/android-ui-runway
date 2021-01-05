package com.kent.android.slim.sample.letv.desktopmanager

import com.kent.android.slim.sample.letv.desktopmanager.bean.ScreenInfo
import java.util.*
import kotlin.collections.ArrayList

class TestItem(val spanNum: Int, val listener: MoveItemListener? = null) {

    private val notFound: Int = -1

    /**
     * 位置向上移动
     * 检查锁，并且和锁前一位交换位置
     * **/
    fun editUpSort(itemList: ArrayList<ScreenInfo>, from: Int, to: Int): ArrayList<ScreenInfo> {
        println("排序前：$itemList")
        // step1.先记录锁的位置
        val locks = LinkedList<Int>()
        for ((index, item) in itemList.withIndex()) {
            if (index < to || index > from) {
                continue
            }
            if (item.locked) {
                locks.add(index)
            }
        }
        // step2.交换目标
        exchangeItem(itemList, from, to)
        println("排序(1)：$itemList")
        // step3.将记录位置和后一位交换
        while (locks.size != 0) {
            val fromPos = locks.removeFirst()
            val toPos = fromPos + 1
            exchangeItem(itemList, fromPos, toPos)
        }
        println("排序(Done)：$itemList")
        return itemList
    }

    /**
     * 位置向下移动
     * 检查锁，并且和锁后一位交换位置
     * **/
    fun editDownSort(itemList: ArrayList<ScreenInfo>, from: Int, to: Int): ArrayList<ScreenInfo> {
        println("排序前：$itemList")
        // step1.记录锁的位置
        val locks = LinkedList<Int>()
        for ((index, item) in itemList.withIndex()) {
            if (index < from || index > to) {
                continue
            }
            if (item.locked) {
                locks.add(index)
            }
        }
        // step2.交换目标
        exchangeItem(itemList, from, to)
        // step3.处理锁：将记录位置和后一位交换
        while (locks.size != 0) {
            val fromPos = locks.removeLast()
            val toPos = fromPos - 1
            exchangeItem(itemList, fromPos, toPos)
        }

        println("排序(Done)：$itemList")
        return itemList
    }

    private fun exchangeItem(itemList: ArrayList<ScreenInfo>, from: Int, to: Int) {
        if (listener == null) {
            val removedItem = itemList.removeAt(from)
            itemList.add(to, removedItem)
        } else {
            listener.onMoveItem(from, to)
        }
        println("moveItem from:$from ,to:$to")
    }

    fun searchPosition(data: ArrayList<ScreenInfo>, currPos: Int, direction: Direction): Int {
        if (data.size <= currPos)
            return notFound

        return when (direction) {
            Direction.LEFT -> {
                handleSearchLeft(data, currPos)
            }
            Direction.RIGHT -> {
                handleSearchRight(data, currPos)
            }
            else -> return notFound
        }
    }

    private fun handleSearchRight(data: ArrayList<ScreenInfo>, currPos: Int): Int {
        if ((currPos + 1) % spanNum == 0) {
            //右边界，不能移动
            return notFound
        }

        //向前找寻下个节点，直到边界
        var offset = 0
        while ((currPos + 1 + offset) % spanNum != 1) {
            offset++ //向前找寻下个节点
            if (currPos + 1 + offset > data.size) {
                return notFound
            }

            val target = currPos + offset
            if (!data[target].locked) {
                return target
            }
        }
        return notFound
    }

    private fun handleSearchLeft(data: ArrayList<ScreenInfo>, currPos: Int): Int {
        if ((currPos + 1) % spanNum == 1) {
            //左边界，不能移动
            return notFound
        }

        //向前找寻下个节点，直到边界
        var offset = 0
        while ((currPos + 1 - offset) % spanNum != 1) {
            offset++ //向前找寻下个节点
            val target = currPos - offset
            if (!data[target].locked) {
                return target
            }
        }
        return notFound
    }

    interface MoveItemListener {
        fun onMoveItem(form: Int, to: Int)
    }


}



