package com.kent.android.slim.sample.letv.desktopmanager

import com.kent.android.slim.sample.letv.desktopmanager.bean.ScreenInfo
import org.junit.Assert
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by songzhukai on 2020/12/31.
 */
class SortHandlerTest {

    val spanNum = 7
    private lateinit var mSortHandler: SortHandler
    private lateinit var mSortHandlerToAdd: SortHandler

    @Before
    fun init() {
        mSortHandler = SortHandler(spanNum, isInUse = true)
        mSortHandlerToAdd = SortHandler(spanNum, isInUse = false)
    }

    class ScreenItem(pkgName: String, val locked: Boolean = false) : ScreenInfo() {
        init {
            super.setPackageName(pkgName)
            super.setLocked(locked)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as ScreenInfo
            return packageName == other.packageName
        }

        override fun toString(): String {
            return "Item(pkgName='$packageName')"
        }
    }

    fun screenItem(pkgName: String, locked: Boolean = false): ScreenInfo {
        return ScreenItem(pkgName, locked)
    }

    /**
     * 帮助建构初始数据
     * ＠Param count 几笔数据
     * ＠Param lockPos 锁的位置 (可多笔)
     */
    private fun initData(count: Int, lockPos: Array<Int>): ArrayList<ScreenInfo> {
        var data = ArrayList<ScreenInfo>()
        for (i in 1..count) {
            data.add(screenItem("A" + i))
        }
        for (i in lockPos) {
            data[i].locked = true
        }
        return data
    }

    @Test
    fun test_InitData() {
        val data = initData(20, arrayOf(0, 5, 6))
        Assert.assertEquals(20, data.size)
        Assert.assertEquals(data[0].locked, true)
        Assert.assertEquals(data[5].locked, true)
        Assert.assertEquals(data[6].locked, true)
        Assert.assertEquals(data[7].locked, false)
    }

    @Test
    fun keyUp_twoLocks_neighbor() {
        assertArrayEquals(
                mSortHandler.editUpSort(
                        mutableListOf(
                                screenItem("A"),
                                screenItem("B", true),
                                screenItem("C", true),
                                screenItem("D")
                        ) as ArrayList<ScreenInfo>, 3, 0).toArray()
                ,
                arrayOf(
                        screenItem("D"),
                        screenItem("B", true),
                        screenItem("C", true),
                        screenItem("A")
                )
        )
    }

    @Test
    fun keyUp_threeLocks_neighbor() {
        assertArrayEquals(
                mSortHandler.editUpSort(
                        mutableListOf(
                                screenItem("A"),
                                screenItem("B", true),
                                screenItem("C", true),
                                screenItem("D", true),
                                screenItem("E")
                        ) as ArrayList<ScreenInfo>, 4, 0).toArray()
                ,
                arrayOf(
                        screenItem("E"),
                        screenItem("B", true),
                        screenItem("C", true),
                        screenItem("D", true),
                        screenItem("A")
                )
        )
    }

    @Test
    fun keyUp_twoLocks_split() {
        assertArrayEquals(
                mSortHandler.editUpSort(
                        mutableListOf(
                                screenItem("A"),
                                screenItem("B", true),
                                screenItem("C"),
                                screenItem("D", true),
                                screenItem("E")
                        ) as ArrayList<ScreenInfo>, 4, 0).toArray()
                ,
                arrayOf(
                        screenItem("E"),
                        screenItem("B", true),
                        screenItem("A"),
                        screenItem("D", true),
                        screenItem("C")
                )
        )
    }

    @Test
    fun keyDown_twoLocks_neighbor() {
        assertArrayEquals(
                mSortHandler.editDownSort(
                        mutableListOf(
                                screenItem("A"),
                                screenItem("B", true),
                                screenItem("C", true),
                                screenItem("D")
                        ) as ArrayList<ScreenInfo>, 0, 3).toArray()
                ,
                arrayOf(
                        screenItem("D"),
                        screenItem("B", true),
                        screenItem("C", true),
                        screenItem("A")
                )
        )
    }

    @Test
    fun keyDown_threeLocks_neighbor() {
        assertArrayEquals(
                mSortHandler.editDownSort(
                        mutableListOf(
                                screenItem("A"),
                                screenItem("B", true),
                                screenItem("C", true),
                                screenItem("D", true),
                                screenItem("E")
                        ) as ArrayList<ScreenInfo>, 0, 4).toArray()
                ,
                arrayOf(
                        screenItem("E"),
                        screenItem("B", true),
                        screenItem("C", true),
                        screenItem("D", true),
                        screenItem("A")
                )
        )
    }

    @Test
    fun keyDown_twoLocks_split() {
        assertArrayEquals(
                mSortHandler.editDownSort(
                        mutableListOf(
                                screenItem("A"),
                                screenItem("B", true),
                                screenItem("C"),
                                screenItem("D", true),
                                screenItem("E")
                        ) as ArrayList<ScreenInfo>, 0, 4).toArray()
                ,
                arrayOf(
                        screenItem("C"),
                        screenItem("B", true),
                        screenItem("E", true),
                        screenItem("D", true),
                        screenItem("A")
                )
        )
    }

    @Test
    fun keyLeft_normal() {
        //左移
        val r1 = mSortHandler.searchPosition(mutableListOf(
                screenItem("A"),
                screenItem("B"),
                screenItem("C"),
                screenItem("D"),
                screenItem("E")) as ArrayList<ScreenInfo>, 3, Direction.LEFT)
        Assert.assertEquals(2, r1)

        //左移边界
        val r2 = mSortHandler.searchPosition(mutableListOf(
                screenItem("A"),
                screenItem("B"),
                screenItem("C"),
                screenItem("D"),
                screenItem("E"),
                screenItem("F"),
                screenItem("G")) as ArrayList<ScreenInfo>, 7, Direction.LEFT)
        Assert.assertEquals(-1, r2)

    }

    @Test
    fun keyLeft_withLock() {
        //有锁的情况
        val r3 = mSortHandler.searchPosition(initData(4, lockPos = arrayOf(2)), 3, Direction.LEFT)
        Assert.assertEquals(1, r3)

        //有锁*2的情况
        val r4 = mSortHandler.searchPosition(initData(4, lockPos = arrayOf(1, 2)), 3, Direction.LEFT)
        Assert.assertEquals(0, r4)

        //有锁、边界情况
        val r5 = mSortHandler.searchPosition(initData(9, lockPos = arrayOf(7)), 8, Direction.LEFT)
        Assert.assertEquals(-1, r5)
    }

    @Test
    fun keyRight_normal() {
        //右移
        val r1 = mSortHandler.searchPosition(initData(4, lockPos = arrayOf()), 2, Direction.RIGHT)
        Assert.assertEquals(3, r1)

        //右边界往右移
        val r2 = mSortHandler.searchPosition(initData(8, lockPos = arrayOf()), 7, Direction.RIGHT)
        Assert.assertEquals(-1, r2)

        //从左边界往右移
        val r3 = mSortHandler.searchPosition(initData(20, lockPos = arrayOf()), 7, Direction.RIGHT)
        Assert.assertEquals(8, r3)

        //右边界往右移
        val r4 = mSortHandler.searchPosition(initData(10, lockPos = arrayOf()), 9, Direction.RIGHT)
        Assert.assertEquals(-1, r4)
    }

    @Test
    fun keyRight_withLock() {
        //右移有锁
        val r2 = mSortHandler.searchPosition(initData(7, lockPos = arrayOf(3)), 2, Direction.RIGHT)
        Assert.assertEquals(4, r2)

        //右移有锁*2
        val r3 = mSortHandler.searchPosition(initData(7, lockPos = arrayOf(3, 4)), 2, Direction.RIGHT)
        Assert.assertEquals(5, r3)

        //有锁、边界情况
        val r5 = mSortHandler.searchPosition(initData(9, lockPos = arrayOf(7)), 6, Direction.RIGHT)
        Assert.assertEquals(-1, r5)
    }

    @Test
    fun keyUp_normal() {
        //上移
        val r1 = mSortHandler.searchPosition(initData(10, lockPos = arrayOf()), 7, Direction.UP)
        Assert.assertEquals(0, r1)

        //上移有锁，往右找
        val r2 = mSortHandler.searchPosition(initData(10, lockPos = arrayOf(0)), 7, Direction.UP)
        Assert.assertEquals(1, r2)

        //上移有锁*2，往右找
        val r3 = mSortHandler.searchPosition(initData(10, lockPos = arrayOf(0, 1)), 7, Direction.UP)
        Assert.assertEquals(2, r3)

        //上移有锁*7，往右找
        val r4 = mSortHandler.searchPosition(initData(10, lockPos = arrayOf(0, 1, 2, 3, 4, 5, 6)), 7, Direction.UP)
        Assert.assertEquals(-1, r4)
    }

    @Test
    fun keyDown_normal() {
        //下移
        val r1 = mSortHandler.searchPosition(initData(10, lockPos = arrayOf()), 1, Direction.DOWN)
        Assert.assertEquals(8, r1)

        //下移
        val r2 = mSortHandler.searchPosition(initData(20, lockPos = arrayOf()), 12, Direction.DOWN)
        Assert.assertEquals(19, r2)

        //下移
        val r3 = mSortHandlerToAdd.searchPosition(initData(2, lockPos = arrayOf()), 1, Direction.DOWN)
        Assert.assertEquals(-1, r3)
    }

    @Test
    fun keyDown_withLock() {
        //下移有锁，往右找
        val r2 = mSortHandler.searchPosition(initData(10, lockPos = arrayOf(8)), 1, Direction.DOWN)
        Assert.assertEquals(9, r2)

        //下移有锁*2，往右找
        val r3 = mSortHandler.searchPosition(initData(20, lockPos = arrayOf(8, 9)), 1, Direction.DOWN)
        Assert.assertEquals(10, r3)

        //下移有锁*7，往右找
        val r4 = mSortHandler.searchPosition(initData(20, lockPos = arrayOf(7, 8, 9, 10, 11, 12, 13)), 1, Direction.DOWN)
        Assert.assertEquals(-1, r4)
    }

    @Test
    fun keyDown_cross() {
        //下移
        val r1 = mSortHandler.searchPosition(initData(10, lockPos = arrayOf()), 8, Direction.DOWN)
        Assert.assertEquals(-2, r1)
    }

    @Test
    fun keyUp_cross() {
        //上移
        val r1 = mSortHandlerToAdd.searchPosition(initData(10, lockPos = arrayOf()), 6, Direction.UP)
        Assert.assertEquals(-2, r1)
    }


}

