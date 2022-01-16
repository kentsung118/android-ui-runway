package com.android.ui.kent.demo.framwork.okhttp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kent.android.slim.sample.BuildConfig
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.framework.okhttp.GitHubApiKt
import kotlinx.android.synthetic.main.activity_okhttp.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

/**
 * Created by Kent Sung on 2022/1/14.
 */
class OkHttpClientActivity : AppCompatActivity() {

    val httpClient: OkHttpClient by lazy { GitHubApiKt.getOKhttpClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp)

        btn_send.setOnClickListener(View.OnClickListener {
            val result = sendRequest()
        })

    }


    class GithubCallback(val context: Activity) : Callback {
        override fun onFailure(call: Call, e: IOException) {
            context.runOnUiThread(Runnable {
                Toast.makeText(context, "onFailure", Toast.LENGTH_LONG).show()
            })

        }

        override fun onResponse(call: Call, response: Response) {
            context.runOnUiThread(Runnable {
                Toast.makeText(context, "onResponse", Toast.LENGTH_LONG).show()
            })
        }
    }


    private fun sendRequest() {

        val request: Request = Request.Builder()
            .url("https://reqres.in/api/users?page=1")
            .build()
        httpClient.newCall(request).enqueue(GithubCallback(this))
    }


}