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
class BackupHelper(private val backupRepository: IBackupRepository) {
    var streamID = -1

    fun updateInfo(model: RestoreEvent, isLocalOnly: Boolean = false) {
        if (isLocalOnly) {
            backupRepository.backupLocalOnly(model)
        } else {
            backupRepository.backup(streamID, model)
        }
    }
}

interface IBackupRepository {
    fun backup(streamID: Int, model: RestoreEvent)
    fun backupLocalOnly(model: RestoreEvent)
}

class BackupRepositoryImpl : IBackupRepository {
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

    override fun backup(streamID: Int, model: RestoreEvent) {
        saveToLocal(model)
        // throw Exception if streamID=-1
        saveToRemote(streamID, model)
    }

    override fun backupLocalOnly(model: RestoreEvent) {
        saveToLocal(model)
    }

    private fun saveToLocal(model: RestoreEvent) {
        // mmkv
        // DatabaseLogic.instance.setDeviceSetting(LOYALTY_POINT_CONFIG, jsonStr)
        BackupStorage.save(model)
    }

    private fun saveToRemote(streamID: Int, model: RestoreEvent){
        val map = mapOf(model.key to model.data)
        // lives/:liveStreamID/mode
        // PATCH api/v1/lives/:liveStreamID/setting
        host!!.backup(map).enqueue(object : Callback<RetrofitActivity.User>{
            override fun onResponse(call: Call<RetrofitActivity.User>, response: Response<RetrofitActivity.User>) {

            }

            override fun onFailure(call: Call<RetrofitActivity.User>, t: Throwable) {

            }
        })
    }
}