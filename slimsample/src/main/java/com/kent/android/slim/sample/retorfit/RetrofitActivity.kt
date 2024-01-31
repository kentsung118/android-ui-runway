package com.kent.android.slim.sample.retorfit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.rejoin.*
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_eventbus.*
import kotlinx.android.synthetic.main.activity_eventbus.view.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 */
class RetrofitActivity : AppCompatActivity() {
    private val TAG = RetrofitActivity::class.java.simpleName
    private var host: Host? = null

    private val client: OkHttpClient by lazy {
        getTrustAllBuilder().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventbus)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .build()
        host = retrofit.create(Host::class.java)

        btn1.setOnClickListener {
            val map = HashMap<String, Hero>()
            map.put("abc", Hero.RockMan("aaaaa"))
            map.put("def", Hero.AI("ggggg", 6))
            host!!.test("kent", map).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    Log.d("lala", "onResponse")
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("lala", "onFailure t=$t")
                }
            })
        }

        btn_backup.setOnClickListener {
            val toSaveEvent = RestoreEvent.CameraEvent(1)
            val liveInfoBackupHelper = LiveInfoBackupHelper(LiveInfoBackupRepositoryImpl())
            liveInfoBackupHelper.streamID = 111
            liveInfoBackupHelper.backup(toSaveEvent)
        }

        val manager = LiveInfoRestoreAbleManager(RejoinConfig(""))

        btn_restore.setOnClickListener {
            manager.initFeature2(LiveInfoRestoreContract.Feature.Camera, object : RestoreAble<RestoreEvent.CameraEvent> {
                override fun initAction(lastModel: RestoreEvent.CameraEvent?) {
                    println("lala initAction lastModel = ${lastModel}")
                }

                override fun restoreAction(lastModel: RestoreEvent.CameraEvent?) {
                    println("lala restoreAction lastModel = ${lastModel}")
                }
            })
        }



        manager.initAction("aaa", RestoreEvent.CameraStatusEvent::class.java, object : RestoreAble<RestoreEvent.CameraStatusEvent> {
            override fun restoreAction(lastModel: RestoreEvent.CameraStatusEvent?) {
                initAction(lastModel)
                println("flag2")

                // to restore feature
            }

            override fun initAction(lastModel: RestoreEvent.CameraStatusEvent?) {
                // init
            }
        })




        println("flag3")

        val liveInfoBackupHelper = LiveInfoBackupHelper(LiveInfoBackupRepositoryImpl())
        val event = RestoreEvent.CameraStatusEvent(
            CameraMode(true, true)
        )
        liveInfoBackupHelper.backup(event)

//        println("Features.CameraStatus.key = ${RejoinContract.Features.CameraStatus.key}")
//        println("Features.CameraStatus.name = ${RejoinContract.Features.CameraStatus.name}")


    }

    interface Host {
        @POST("/test")
        fun test(
            @Query("categoryID") key: String,
            @Body map: Map<String, @JvmSuppressWildcards Hero>
        ): Call<User>

        @POST("/backup")
        fun backup(
            @Body map: Map<String, @JvmSuppressWildcards Any>
        ): Call<User>

        @POST("/{streamID}/setting")
        fun setting(
            @Path("streamID") streamID: String,
            @Body map: Map<String, @JvmSuppressWildcards Any>
        ): Call<User>

        @POST("/{streamID}/mode")
        fun mode(
            @Path("streamID") streamID: String,
            @Body map: Map<String, @JvmSuppressWildcards Any>
        ): Call<User>
    }

    private val BASE_URL = "https://api.github.com/"

    data class User(val name: String)


    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, RetrofitActivity::class.java)
            activity.startActivity(intent)
        }
    }

    sealed class Hero : Parcelable {
        @Parcelize
        class RockMan(val name: String) : Hero()

        @Parcelize
        class AI(val type: String, val value: Int) : Hero()
    }

    fun getTrustAllBuilder(): OkHttpClient.Builder {
        //信任所有憑證
        return try {
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }
            })
            builder.hostnameVerifier { hostname, session -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}