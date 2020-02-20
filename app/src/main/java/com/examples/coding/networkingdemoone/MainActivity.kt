package com.examples.coding.networkingdemoone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.examples.coding.networkingdemoone.datasource.remote.httpurlconnection.HttpUrlConnectionHelper
import com.examples.coding.networkingdemoone.datasource.remote.okhttp.OkHttpHelper
import com.examples.coding.networkingdemoone.model.User.UserResponce
import com.examples.coding.networkingdemoone.view.adapter.UserAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.btnExecuteHttpUrlConnCall -> executeHttpUrlConnCall()
            R.id.btnExecuteAsyncOkHttp -> executeAsyncOkHttpCall()
        }
    }

    fun executeAsyncOkHttpCall() {
        val randomUserURL = "https://randomuser.me/api/?results=10"
        val okHttpHelper = OkHttpHelper()
        okHttpHelper.makeAsyncApiCall(randomUserURL)
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
