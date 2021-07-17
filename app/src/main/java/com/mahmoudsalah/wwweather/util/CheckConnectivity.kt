package com.mahmoudsalah.wwweather.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

class CheckConnectivity {
    private val appContext = MyApplication.getContext()

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(): Boolean {
        val cm =
                appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n)
            return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc!!.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
            )
        }

        return false
    }
}

