package com.examples.coding.networkingdemoone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.examples.coding.networkingdemoone.datasource.remote.chuckNorrisJokesURL
import com.examples.coding.networkingdemoone.datasource.remote.httpurlconnection.HttpUrlConnectionHelper
import com.examples.coding.networkingdemoone.datasource.remote.okhttp.OkHttpAsyncTsk
import com.examples.coding.networkingdemoone.datasource.remote.okhttp.OkHttpHelper
import com.examples.coding.networkingdemoone.datasource.remote.randomUserFullURL
import com.examples.coding.networkingdemoone.datasource.remote.retrofit.RetrofitHelper
import com.examples.coding.networkingdemoone.model.ChuckNorrisResponse.JokeResponse
import com.examples.coding.networkingdemoone.model.User.UserResponce
import com.examples.coding.networkingdemoone.view.adapter.UserAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.btnExecuteHttpUrlConnCall -> executeHttpUrlConnCall()
            R.id.btnExecuteAsyncOkHttp -> executeAsyncOkHttpCall()
            R.id.btnExecuteSyncOkHttp -> executeSyncOkHttpCall()
            R.id.btnExecuteAsyncRetrofit -> executeAsyncRetrofitCall()
        }
    }

    fun executeAsyncOkHttpCall() {
        val okHttpHelper = OkHttpHelper(cacheDir)
        try {
            okHttpHelper.makeAsyncApiCall(randomUserFullURL)
        } catch(e : Exception) {
            okHttpHelper.makeAsyncApiCall(chuckNorrisJokesURL)
        }
    }

    fun executeSyncOkHttpCall() {
        val okHttpAsyncTsk = OkHttpAsyncTsk(cacheDir)
        okHttpAsyncTsk.execute()
    }

    fun executeAsyncRetrofitCall() {
        val retrofitHelper = RetrofitHelper()
        retrofitHelper.startChuckNorrisRequest(cacheDir)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserResponse(userResponse: UserResponce) {
        rvUserList.layoutManager = LinearLayoutManager(this)
        rvUserList.adapter = UserAdapter(userResponse.results)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onJokeResponse(jokeResponse: JokeResponse) {
        Log.d("TAG", jokeResponse.value.joke)
        Toast.makeText(this, jokeResponse.value.joke, Toast.LENGTH_LONG).show()
    }

    fun executeHttpUrlConnCall() {
        val randomUserURL = "https://randomuser.me/api/?results=10"
        val httpUrlConnectionHelper = HttpUrlConnectionHelper()
        var jsonString = ""
        Thread(
            Runnable
            {
                jsonString = httpUrlConnectionHelper.getResponse(randomUserURL)
                Log.d("TAG", jsonString)

                if(jsonString.isNotBlank()) {
                    val userResponse = Gson().fromJson<UserResponce>(jsonString, UserResponce::class.java)
                    Log.d("TAG", "FIRST RESPONSES FIRST NAME = ${userResponse.results[0].name.first}")
                    runOnUiThread {
                        rvUserList.layoutManager = LinearLayoutManager(this)
                        rvUserList.adapter = UserAdapter(userResponse.results)
                    }
                }
            }
        ).start()



    }
}
