package com.kent.android.slim.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.gson.Gson
import com.kent.android.slim.sample.room.AppDatabase
import com.kent.android.slim.sample.room.User
import com.kent.android.slim.sample.room.UserDao
import com.kent.android.slim.sample.util.CoroutineExecutor
import kotlinx.android.synthetic.main.activity_room.*
import java.lang.StringBuilder

/**
 * Created by Kent Sung on 2021/10/14.
 */
class RoomActivity : AppCompatActivity() {

    lateinit var userDao: UserDao
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        userDao = db.userDao();

        btn_add.setOnClickListener {
            CoroutineExecutor.launchIO {
                val user = User(i++, "kent", "song")
                userDao.insertAll(user)
                queryUser()
            }

        }
    }

    fun queryUser() {
        val allUser = userDao.getAll()

        runOnUiThread {
            val sb = StringBuilder()
            for (m in allUser) {
                sb.append(Gson().toJson(m))
                sb.append(System.lineSeparator())
            }
            text_result.text = sb.toString()
        }
    }
}