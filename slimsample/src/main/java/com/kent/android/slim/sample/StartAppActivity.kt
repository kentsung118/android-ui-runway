package com.kent.android.slim.sample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_startapp.*
import java.util.*

/**
 * Created by songzhukai on 2020/9/21.
 */

class StartAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startapp)

        uri_btn.setOnClickListener {
            try {
                val intent = Intent("android.intent.action.VIEW")
                val uriData = "kent://ui-runway/home"
                intent.data = Uri.parse(uriData)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        action_btn.setOnClickListener {
            try {
                val intent = Intent()
                //                    intent.setPackage("com.cibn.tv");
                intent.action = "com.tv.yingshi.kumiao"
                intent.putExtra("uri_kumiao", "cibntv_yingshi://detail?url=tv/v3/show/detail?id=208322&fullscreen=&fullback=&from=letvp1")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        Log.d("kentsung", "flag1")
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_TIME_TICK)
        registerReceiver(mTimeTickReceiver, filter)
    }

    private val mTimeTickReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent?.action
            action?.let {
                if (Intent.ACTION_TIME_TICK == it) {
                    Log.d("kentsung", action)
                    val calendar =  Calendar.getInstance()
                    calendar.time = Date()
                    when (val minute = calendar.get(Calendar.MINUTE)) {
                        in 0..5,
                        in 11..15,
                        in 21..25,
                        in 31..35,
                        in 41..45,
                        in 51..55,
                        -> {
                            Log.d("kentsung", ""+minute)
                        }
                        in 6..10,
                        in 16..20,
                        in 26..30,
                        in 36..40,
                        in 46..50,
                        in 56..59,
                        -> {
                            Log.d("kentsung", ""+minute)
                        }
                    }


                }
            }

        }
    }
}