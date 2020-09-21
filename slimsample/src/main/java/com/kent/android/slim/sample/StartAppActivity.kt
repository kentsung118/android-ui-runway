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
                val intent = Intent("com.dangbei.dbmusic.action.foreign_router")
                val uriData = "music://com.dangbei.dbmusic/foreign_router?path=%7B%22jump_type%22%3A%221%22%2C%22package%22%3A%22%22%2C%22link%22%3A%22music%3A%5C%2F%5C%2Fplay%3Ftype%3D4%26id%3D261209789%22%7D"
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