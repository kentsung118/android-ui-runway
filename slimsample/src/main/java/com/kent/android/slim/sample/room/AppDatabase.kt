package com.kent.android.slim.sample.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Kent Sung on 2021/10/14.
 */
@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}