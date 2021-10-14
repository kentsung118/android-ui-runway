package com.kent.android.slim.sample.room.gift

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Kent Sung on 2021/10/14.
 */
@Entity(tableName = "gift_usage")
data class GiftUsage(@field:PrimaryKey val id: String, val lastUseTime: String)
