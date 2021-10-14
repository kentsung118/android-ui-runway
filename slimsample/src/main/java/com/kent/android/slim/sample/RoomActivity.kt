package com.kent.android.slim.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.gson.Gson
import com.kent.android.slim.sample.room.AppDatabase
import com.kent.android.slim.sample.room.gift.GiftUsage
import com.kent.android.slim.sample.room.gift.GiftUsageDao
import com.kent.android.slim.sample.room.user.User
import com.kent.android.slim.sample.room.user.UserDao
import com.kent.android.slim.sample.util.CoroutineExecutor
import kotlinx.android.synthetic.main.activity_room.*
import java.lang.StringBuilder

/**
 * Created by Kent Sung on 2021/10/14.
 */
class RoomActivity : AppCompatActivity() {

    lateinit var userDao: UserDao
    lateinit var giftDao: GiftUsageDao
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        userDao = db.userDao();
        giftDao = db.giftUsageDao()

        btn_add.setOnClickListener {
            CoroutineExecutor.launchIO {
                val user = User(i++, "kent", "song")
                userDao.insertAll(user)
                queryAll()
            }

        }

        btn_gift_add.setOnClickListener {
            CoroutineExecutor.launchIO {
                i++
                val giftUseage = GiftUsage("gift_id_1", "2020-11-08"+i)
                giftDao.insert(giftUseage)
                queryAll()
            }
        }

        btn_gift_delete.setOnClickListener{
            CoroutineExecutor.launchIO {
//                giftDao.deleteById("gift_id_1")
                giftDao.deleteAll()
                queryAll()
            }
        }
    }

    fun queryAll() {
        val allUser = userDao.getAll()
        val allGift = giftDao.getAll()

        runOnUiThread {
            val sb = StringBuilder()
            for (m in allUser) {
                sb.append(Gson().toJson(m))
                sb.append(System.lineSeparator())
            }
            for(item in allGift){
                sb.append(Gson().toJson(item))
                sb.append(System.lineSeparator())
            }

            text_result.text = sb.toString()
        }
    }
}