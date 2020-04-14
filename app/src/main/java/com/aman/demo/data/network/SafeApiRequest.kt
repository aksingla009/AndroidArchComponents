package com.aman.demo.data.network

import com.aman.demo.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()

        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            var msg = StringBuilder()
            error?.let {
                try {
                    //message is the key in response if we get json error such that invalid credentials and so on
                    msg.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                    // If we do not get JSON response such as 404, 301 and so on HTML responses
                }
                msg.append("\n")
            }
            msg.append("Error Code is : ${response.code()}")
            throw ApiException(msg.toString())

        }
    }

}