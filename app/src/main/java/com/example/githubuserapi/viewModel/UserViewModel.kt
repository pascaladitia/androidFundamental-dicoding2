package com.example.githubuserapi.viewModel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapi.main.MainActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.pascal.githubuserapi.data.DataUser
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class UserViewModel: ViewModel() {

    val dataUser = MutableLiveData<ArrayList<DataUser>>()

    fun setUser(id: String) {
        val listData = ArrayList<DataUser>()

        val url = "https://api.github.com/search/users?q=$id"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 2e926c24ee8fd753f73547316a8252f0fa0b0c71")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val item = responseObject.getJSONArray("items")

                    for (i in 0 until item.length()) {
                        val user = item.getJSONObject(i)
                        val dataUser = DataUser()
                        dataUser.login = user.getString("login")
                        dataUser.avatars = user.getString("avatar_url")
                        listData.add(dataUser)
                    }
                    dataUser.postValue(listData)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getUser(): LiveData<ArrayList<DataUser>> {
        return dataUser
    }
}
