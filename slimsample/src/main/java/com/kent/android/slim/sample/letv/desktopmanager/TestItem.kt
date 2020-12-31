package com.kent.android.slim.sample.letv.desktopmanager

class TestItem {
    /**
     * 位置向上移动
     * 检查锁，并且和锁前一位交换位置
     * **/
    fun editUpSort(itemList: ArrayList<Item>, from: Int, to: Int): ArrayList<Item> {

        println("排序前：$itemList")
        val removedItem = itemList.removeAt(from)
        itemList.add(to, removedItem)
        println("排序(1)：$itemList")

        var beginPoint = to
        val endPoint = from
        var moveNum = 0

        while (beginPoint < endPoint) {

            var to: Int? = null
            for ((index, item) in itemList.withIndex()) {
                //最后一笔，边界
                if (index == endPoint)
                    beginPoint = index

                //若慢指针已检查过，忽略
                if (index < beginPoint)
                    continue

                //仅有lock需处理，其他忽略
                if (item.lock && to != null) {
                    val lockItem = itemList.removeAt(index)
                    itemList.add(to, lockItem)
                    //处理move动画 move(to, index)
                    println("moveItem from:$index ,to:$to")
                    moveNum++
                    beginPoint = index
                    break
                } else {
                    to = index
                    continue
                }
            }
        }

        println("排序(2)：$itemList")
        println("moveItem 次数：$moveNum")
        return itemList
    }

    /**
     * 位置向下移动
     * 检查锁，并且和锁后一位交换位置
     * **/
    fun editDownSort(itemList: ArrayList<Item>, from: Int, to: Int): ArrayList<Item> {

        println("排序前：$itemList")
        val removedItem = itemList.removeAt(from)
        itemList.add(to, removedItem)
        println("排序(1)：$itemList")

        var beginPoint = to
        var endPoint = from
        var moveNum = 0

        while (beginPoint > endPoint) {
            var to: Int? = null

            val count = itemList.size
            for (index in count - 1 downTo 0) {
                println("i$index")
                //最后一笔，边界
                if (index == endPoint)
                    beginPoint = index

                //若慢指针已检查过，忽略
                if (index > beginPoint)
                    continue

                //仅有lock需处理，其他忽略
                val item = itemList[index]
                if (item.lock && to != null) {
                    val lockItem = itemList.removeAt(index)
                    itemList.add(to, lockItem)
                    //处理move动画 move(to, index)
                    println("moveItem from:$index ,to:$to")
                    moveNum++
                    beginPoint = index
                    break
                } else {
                    to = index
                    continue
                }

            }
        }

        println("排序(2)：$itemList")
        println("moveItem 次数：$moveNum")
        return itemList
    }
}


class Item(val id: String, val lock: Boolean = false) {
    override fun toString(): String {
        return "Item(id='$id')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        return id == other.id
    }


}


fun main() {

    val items = mutableListOf(
            Item("A"),
            Item("B", true),
            Item("D"),
            Item("C", true),
            Item("E"),
            Item("F"),
            Item("G")
    )
    val itemTest = TestItem()
    itemTest.editUpSort(items as ArrayList<Item>, 4, 0)

}


