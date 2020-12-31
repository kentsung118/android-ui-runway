package com.kent.android.slim.sample.letv.desktopmanager

import org.junit.Assert.*
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

    @Test
    fun test_editUpSort_CrossTwoLock() {
        assertArrayEquals(
                testItem.editUpSort(
                        mutableListOf(
                            Item("A"),
                            Item("B", true),
                            Item("C", true),
                            Item("D")
                        ) as ArrayList<Item>, 3, 0).toArray()
                ,
                arrayOf(
                        Item("D"),
                        Item("B", true),
                        Item("C", true),
                        Item("A")
                )
        )
    }

    @Test
    fun test_editUpSort_ThreeTwoLock() {
        assertArrayEquals(
                testItem.editUpSort(
                        mutableListOf(
                                Item("A"),
                                Item("B", true),
                                Item("C", true),
                                Item("D", true),
                                Item("E")
                        ) as ArrayList<Item>, 4, 0).toArray()
                ,
                arrayOf(
                        Item("E"),
                        Item("B", true),
                        Item("C", true),
                        Item("D", true),
                        Item("A")
                )
        )
    }

    @Test
    fun test_editUpSort_CrossTwoLockWithSpace() {
        assertArrayEquals(
                testItem.editUpSort(
                        mutableListOf(
                                Item("A"),
                                Item("B", true),
                                Item("C"),
                                Item("D", true),
                                Item("E")
                        ) as ArrayList<Item>, 4, 0).toArray()
                ,
                arrayOf(
                        Item("E"),
                        Item("B", true),
                        Item("A"),
                        Item("D", true),
                        Item("C")
                )
        )
    }

    @Test
    fun test_editDownSort_CrossTwoLock() {
        assertArrayEquals(
                testItem.editDownSort(
                        mutableListOf(
                                Item("A"),
                                Item("B", true),
                                Item("C", true),
                                Item("D")
                        ) as ArrayList<Item>, 0, 3).toArray()
                ,
                arrayOf(
                        Item("D"),
                        Item("B", true),
                        Item("C", true),
                        Item("A")
                )
        )
    }

    @Test
    fun test_editDownSort_CrossThreeLock() {
        assertArrayEquals(
                testItem.editDownSort(
                        mutableListOf(
                                Item("A"),
                                Item("B", true),
                                Item("C", true),
                                Item("D", true),
                                Item("E")
                        ) as ArrayList<Item>, 0, 4).toArray()
                ,
                arrayOf(
                        Item("E"),
                        Item("B", true),
                        Item("C", true),
                        Item("D", true),
                        Item("A")
                )
        )
    }

    @Test
    fun test_editDownSort_TwoLockWithSpace() {
        assertArrayEquals(
                testItem.editDownSort(
                        mutableListOf(
                                Item("A"),
                                Item("B", true),
                                Item("C"),
                                Item("D", true),
                                Item("E")
                        ) as ArrayList<Item>, 0, 4).toArray()
                ,
                arrayOf(
                        Item("C"),
                        Item("B", true),
                        Item("E", true),
                        Item("D", true),
                        Item("A")
                )
        )
    }
}

