package com.kent.android.slim.sample.letv.desktopmanager

/**
 * Created by songzhukai on 2020/12/30.
 */
object CalculateUtil {

    /**
     * 是否为最后一行
     * @param itemCount adpater.getItemCount
     * @param currPos item 位於 Rv 當前位置
     * @param spanCount 一列有几个item
     */
    fun isInLastRow(itemCount: Int, currPos: Int, spanCount: Int): Boolean {
        val rvMaxRow = if (itemCount % spanCount == 0) {
            itemCount / spanCount
        } else {
            itemCount / spanCount + 1
        }

        val currRow = if ((currPos + 1) % spanCount == 0) {
            (currPos + 1) / spanCount
        } else {
            (currPos + 1) / spanCount + 1
        }
        return rvMaxRow == currRow
    }

    /**
     * 是否在该行最后一个位置
     */
    fun isOnRowLastPos(currPos: Int, spanCount: Int): Boolean {
        return (currPos + 1) % spanCount == 0
    }

    /**
     * 是否在该行第一个位置
     */
    fun isOnRowFirstPos(currPos: Int, spanCount: Int): Boolean {
        return (currPos + 1) % spanCount == 1
    }


}