package com.examples.coding.networkingdemoone.datasource.remote.retrofit

import android.util.Log
import com.examples.coding.networkingdemoone.datasource.remote.BASE_URL_CHUCK_NORRIS
import com.examples.coding.networkingdemoone.datasource.remote.BASE_URL_RANDOM_USER
import com.examples.coding.networkingdemoone.datasource.remote.okhttp.OkHttpHelper
import com.examples.coding.networkingdemoone.datasource.remote.retrofit.ChuckNorrisService.Companion.getChuckNorrisJokeCallService
import com.examples.coding.networkingdemoone.model.ChuckNorrisResponse.JokeResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class RetrofitHelper {

    fun getRetrofitInstance(isRandomUser: Boolean, cacheFile : File): Retrofit {
        val builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpHelper(cacheFile).getClient())
        if (isRandomUser) {
            builder.baseUrl(BASE_URL_RANDOM_USER)
        } else {
            builder.baseUrl(BASE_URL_CHUCK_NORRIS)
        }
        return builder.build()
    }

    fun getRandomUserCallService(cacheFile : File) =
        getRetrofitInstance(true, cacheFile).create(RondomUserService::class.java)


    fun startChuckNorrisRequest(cacheFile: File) {
        getChuckNorrisJokeCallService(cacheFile)
            .getRandomJokes("random")
            .enqueue(object : Callback<JokeResponse> {
                override fun onResponse(call: Call<JokeResponse>, response: Response<JokeResponse>) {
                    EventBus.getDefault().post(response.body())
                }

                override fun onFailure(call: Call<JokeResponse>, t: Throwable) {
                    Log.e("TAG", "ERROR IN RETROFIT -->", t)
                }
            })
    }

}