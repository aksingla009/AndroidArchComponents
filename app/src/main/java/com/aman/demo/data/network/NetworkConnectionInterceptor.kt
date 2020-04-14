package com.aman.demo.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.aman.demo.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        //inside this chain instance we have the request
        //before proceeding with request we will check if network is available or not in our device
        //that is why we will create a new function isInternetAvailable and call it from here
        if (!isInternetAvailable()) {
            throw NoInternetException("Make sure you have an active data connection")
        } else {
            return chain.proceed(chain.request())
        }
    }

    private fun isInternetAvailable(): Boolean {
        var isActiveNetwork = false;
        // we will get connectivity manager from system service
        // for system service we need application context so get it from the constructor
        //val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        //to cast it as Connectivity manager we use keyword AS
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                isActiveNetwork = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return isActiveNetwork

    }
}