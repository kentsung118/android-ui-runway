package com.kent.android.slim.sample.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kent.android.slim.sample.room.gift.GiftUsage
import com.kent.android.slim.sample.room.gift.GiftUsageDao
import com.kent.android.slim.sample.room.user.User
import com.kent.android.slim.sample.room.user.UserDao

/**
 * Created by Kent Sung on 2021/10/14.
 */
@Database(entities = arrayOf(User::class, GiftUsage::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun giftUsageDao(): GiftUsageDao
}