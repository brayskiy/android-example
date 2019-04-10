package com.brayskiy.example.util

import android.content.Context
import android.net.wifi.p2p.WifiP2pDevice.CONNECTED
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by brayskiy on 01/31/19.
 */

object Network {
    fun isNetworkOnline(context: Context): Boolean {
        try {
            val connectivityService = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (connectivityService != null) {
                var networkInfo = connectivityService.getNetworkInfo(0)
                if (networkInfo != null && networkInfo.state === NetworkInfo.State.CONNECTED) {
                    return true
                } else {
                    networkInfo = connectivityService.getNetworkInfo(1)
                    if (networkInfo != null && networkInfo.state === NetworkInfo.State.CONNECTED)
                        return true
                }
            }
        } catch (e: Exception) {
            return false
        }

        return false

    }
}