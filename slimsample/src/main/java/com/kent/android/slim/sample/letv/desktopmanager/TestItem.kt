package com.kent.android.slim.sample.letv.desktopmanager

fun editUpSort(itemList: ArrayList<Item>, from: Int, to: Int) {

    println("排序前：$itemList")
    val removedItem = itemList.removeAt(from)
    itemList.add(to, removedItem)
    println("排序(1)：$itemList")

    //检查是否有锁，找到锁，并且和锁前一位交换


    var sPoint = 0
    var moveNum = 0

    while (sPoint < itemList.size - 1) {

        var to: Int? = null
        for ((index, item) in itemList.withIndex()) {
            //最后一笔，边界
            if (index == itemList.size -1)
                sPoint = index

            //若慢指针已检查过，忽略
            if (index < sPoint)
                continue

            //仅有lock需处理，其他忽略
            if (item.lock && to != null) {
                val lockItem = itemList.removeAt(index)
                itemList.add(to, lockItem)
                //处理move动画 move(to, index)
                moveNum ++
                sPoint = index
                break
            } else {
                to = index
                continue
            }
        }
    }

    println("排序(2)：$itemList")
    println("moveItem 次数：$moveNum")
}


class Item(val id: String, val lock: Boolean = false) {
    override fun toString(): String {
        return "Item(id='$id')"
    }

}


fun main() {

    val items = mutableListOf(
            Item("A"),
            Item("B", true),
            Item("D"),
            Item("C", true),
            Item("E")
    )
    editUpSort(items as ArrayList<Item>, 4, 0)

}


