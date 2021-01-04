package com.kent.android.slim.sample.letv.desktopmanager

import com.kent.android.slim.sample.letv.desktopmanager.bean.ScreenInfo
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by songzhukai on 2020/12/31.
 */
class TestItemKtTest {

    var testItem = TestItem()

    @Before
    fun init() {
    }

    class ScreenItem(pkgName: String, val locked: Boolean= false) : ScreenInfo(){
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

    @Test
    fun keyUp_twoLocks_neighbor() {
        assertArrayEquals(
                testItem.editUpSort(
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
                testItem.editUpSort(
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
                testItem.editUpSort(
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
                testItem.editDownSort(
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
                testItem.editDownSort(
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
                testItem.editDownSort(
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
}

