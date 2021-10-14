package com.kent.android.slim.sample.room.gift

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by Kent Sung on 2021/10/14.
 */
@Dao
interface GiftUsageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gift: GiftUsage)

    @Query("DELETE FROM gift_usage WHERE `id` = :id")
    fun deleteById(id: String)

    @Query("SELECT * FROM gift_usage WHERE `id` = :id")
    fun queryById(id: String): GiftUsage

    @Query("SELECT * FROM gift_usage")
    fun getAll(): List<GiftUsage>

    @Query("DELETE FROM gift_usage")
    fun deleteAll()
}