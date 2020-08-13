package com.example.githubuserapi.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.pascal.githubuserapi.data.DataUser
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowingViewModel: ViewModel() {

    val listFollowingUser = MutableLiveData<ArrayList<DataUser>>()

    fun setFollowingUser(id: String) {
        val listData = ArrayList<DataUser>()

        val url = "https://api.github.com/users/$id/following"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 2e926c24ee8fd753f73547316a8252f0fa0b0c71")
        client.addHeader("User-agent", "request")
        client.get(url, object: AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val following = responseArray.getJSONObject(i)
                        val dataUser = DataUser()
                        dataUser.apply {
                            login = following.getString("login")
                            avatars = following.getString("avatar_url")
                        }
                        listData.add(dataUser)
                    }
                    listFollowingUser.postValue(listData)

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

    fun getFollowingUser(): LiveData<ArrayList<DataUser>> {
        return listFollowingUser
    }

}