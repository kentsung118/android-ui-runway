package com.kent.android.slim.sample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_startapp.*

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
    }
}