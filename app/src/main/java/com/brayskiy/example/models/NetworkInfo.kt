package com.brayskiy.example.models

import java.net.InetAddress

/**
 * Created by brayskiy on 01/14/19.
 */

data class HostItem(
    val hostName: String,
    val ipAddress: String,
    val inetAddress: InetAddress,
    val isReachable: Boolean
)

data class NetworkInfo(
    val extraData: String,
    val isConnected: Boolean,
    val myIpAddress: String,
    val allHosts: ArrayList<HostItem>
)
