package com.android.ui.kent.demo.framwork.okhttp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kent.android.slim.sample.BuildConfig
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.framework.okhttp.GitHubApiKt
import kotlinx.android.synthetic.main.activity_okhttp.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.connection.RealCall
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit
import org.json.JSONObject
import org.json.JSONException








/**
 * Created by Kent Sung on 2022/1/14.
 */
class OkHttpClientActivity : AppCompatActivity() {

    val httpClient: OkHttpClient by lazy { GitHubApiKt.getOKhttpClient() }
    val httpClientCache: OkHttpClient by lazy { GitHubApiKt.getCacheOnlyOKhttpClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp)

        btn_send.setOnClickListener(View.OnClickListener {
            val result = sendRequest(httpClient)
        })

        cache_only_send.setOnClickListener(View.OnClickListener {
            val result = sendRequest(httpClientCache)
        })

    }


    class GithubCallback(val context: Activity) : Callback {
        override fun onFailure(call: Call, e: IOException) {
            context.runOnUiThread(Runnable {
                Toast.makeText(context, "onFailure", Toast.LENGTH_LONG).show()
            })

        }

        override fun onResponse(call: Call, response: Response) {

//            try {
//                val responseObj = JSONObject(response.body!!.string())
//                Log.d("kent flag", "responseObj: $responseObj")
//            } catch (e: JSONException) {
//                Log.d("kent flag", "JSONException: $e")
//                e.printStackTrace()
//            }
            context.runOnUiThread(Runnable {
                Toast.makeText(context, "onResponse", Toast.LENGTH_LONG).show()
                Log.d("kent flag", "kent flag onResponse messaage = " + response.message)
                Log.d("kent flag", "kent flag onResponse code = " + response.code)
            })
        }
    }


    private fun sendRequest(httpClient:OkHttpClient) {
        val request: Request = Request.Builder()
            .url("https://6200937efdf5090017249518.mockapi.io/api/v1/usr")
//            .cacheControl(
//                CacheControl.Builder()
////                .maxStale(365, TimeUnit.DAYS)
////                .maxAge(10, TimeUnit.SECONDS)
////                .onlyIfCached()
//                    .noCache()
//                    .build()
//            )
            .tag("123")
//            .tag(String.javaClass, "456")
            .build()
        var call = httpClient.newCall(request)
//        val newRequest = call.request().newBuilder().header("kentTest", "value").build()
//        if (call is RealCall) {
//            call = RealCall(call.client, newRequest, call.forWebSocket)
//        }
        call.enqueue(GithubCallback(this))
    }


}