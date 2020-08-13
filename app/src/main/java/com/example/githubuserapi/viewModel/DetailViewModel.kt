package com.example.githubuserapi.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.pascal.githubuserapi.data.DataUser
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailViewModel: ViewModel() {

    val dataUser = MutableLiveData<ArrayList<DataUser>>()

    fun setUserDetail(username: String) {
        val url = "https://api.github.com/users/$username"
        val listData = ArrayList<DataUser>()

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 2e926c24ee8fd753f73547316a8252f0fa0b0c71")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val userDetail = DataUser()
                    userDetail.apply {
                        login = responseObject.getString("login")
                        name = responseObject.getString("name")
                        location = responseObject.getString("location")
                        company = responseObject.getString("company")
                        repository = responseObject.getInt("public_repos")
                        followers = responseObject.getInt("followers")
                        following = responseObject.getInt("following")
                        avatars = responseObject.getString("avatar_url")
                    }
                    listData.add(userDetail)

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

    fun getDetailUser(): LiveData<ArrayList<DataUser>> {
        return dataUser
    }
}
