package com.examples.coding.networkingdemoone.datasource.remote.okhttp

import android.os.AsyncTask
import com.examples.coding.networkingdemoone.datasource.remote.chuckNorrisJokesURL
import com.examples.coding.networkingdemoone.model.ChuckNorrisResponse.JokeResponse
import com.examples.coding.networkingdemoone.model.User.UserResponce
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.lang.Exception

class OkHttpAsyncTsk(val cacheFile: File) : AsyncTask<Void, Void, String>() {
    lateinit var userResponce: UserResponce
    override fun doInBackground(vararg p0: Void?): String {
        try {
            val okHttpHelper = OkHttpHelper(cacheFile)
            val randomUserURL = "https://randomuser.me/api/?results=10"
            val json = okHttpHelper.makeSyncApiCall(randomUserURL)
            userResponce = Gson().fromJson<UserResponce>(json, UserResponce::class.java)
            return json
        } catch (e : Exception) {
            val okHttpHelper = OkHttpHelper(cacheFile)
            val json = okHttpHelper.makeSyncApiCall(chuckNorrisJokesURL)
            return json
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if(::userResponce.isInitialized) {
            EventBus.getDefault().post(userResponce)
        } else {
            val jokeResponse = Gson().fromJson<JokeResponse>(result, JokeResponse::class.java)
            EventBus.getDefault().post(jokeResponse)
        }
    }


}