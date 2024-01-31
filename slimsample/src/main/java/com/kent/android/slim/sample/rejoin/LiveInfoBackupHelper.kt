package com.kent.android.slim.sample.rejoin

import com.google.gson.Gson
import com.kent.android.slim.sample.framework.okhttp.GitHubApiKt.BASE_URL
import com.kent.android.slim.sample.framework.okhttp.GitHubApiKt.getTrustAllBuilder
import com.kent.android.slim.sample.retorfit.RetrofitActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Kent Sung on 2023/1/18.
 */

interface LiveInfoBackup {
    fun backup(model: RestoreEvent)
    fun backupLocalOnly(model: RestoreEvent)
}

class LiveInfoBackupHelper(
    private val backupRepository: LiveInfoBackupRepository
) : LiveInfoBackup {
    // 取決於 streamID 好不好取？
    var streamID = -1

    override fun backup(model: RestoreEvent) {
        backupRepository.saveToLocal(model)
        when (model.releatedFeatrue) {
            LiveInfoRestoreContract.Feature.StreamMode -> backupRepository.sendChangeStreamMode(streamID, model)
            else -> {
                backupRepository.sendSetting(streamID, model)
            }
        }
    }

    override fun backupLocalOnly(model: RestoreEvent) {
        backupRepository.saveToLocal(model)
    }
}

interface LiveInfoBackupRepository {
    fun sendSetting(streamID: Int, model: RestoreEvent)
    fun sendChangeStreamMode(streamID: Int, model: RestoreEvent)
    fun saveToLocal(model: RestoreEvent)
}

class LiveInfoBackupRepositoryImpl : LiveInfoBackupRepository {
    private val gson = Gson()
    private var host: RetrofitActivity.Host? = null
    private val client: OkHttpClient by lazy {
        getTrustAllBuilder().build()
    }

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client)
            .build()
        host = retrofit.create(RetrofitActivity.Host::class.java)
    }

    override fun sendSetting(streamID: Int, model: RestoreEvent) {
        val map = mapOf(model.releatedFeatrue.backendKey to model.data)
        // lives/:liveStreamID/mode
        // PATCH api/v1/lives/:liveStreamID/setting
        host!!.backup(map).enqueue(object : Callback<RetrofitActivity.User> {
            override fun onResponse(call: Call<RetrofitActivity.User>, response: Response<RetrofitActivity.User>) {

            }

            override fun onFailure(call: Call<RetrofitActivity.User>, t: Throwable) {

            }
        })
    }

    override fun saveToLocal(model: RestoreEvent) {
        // mmkv
        // DatabaseLogic.instance.setDeviceSetting(LOYALTY_POINT_CONFIG, jsonStr)
        BackupStorage.save(model)
    }

    override fun sendChangeStreamMode(streamID: Int, model: RestoreEvent) {
        // Apiclient call
    }

}