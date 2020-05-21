package com.nnbinh.mvidemo.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.nnbinh.mvidemo.network.NetWorkState.OFFLINE
import com.nnbinh.mvidemo.network.NetWorkState.ONLINE

class NetworkMonitor(private var listener: NetworkListener? = null) : NetworkCallback() {
  companion object {
    fun isConnected(ctx: Context): Boolean {
      val manager = ctx.getSystemService(
          Context.CONNECTIVITY_SERVICE
      ) as ConnectivityManager

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val activeNetwork = manager.activeNetwork ?: return false
        val networkCap = manager.getNetworkCapabilities(activeNetwork) ?: return false

        return networkCap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            networkCap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            networkCap.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
      }

      val activeNetworkInfo = manager.activeNetworkInfo
      return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
  }

  private var latestState: NetWorkState? = null

  private var networkRequest: NetworkRequest = NetworkRequest.Builder()
      .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
      .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
      .build()

  fun enable(context: Context) {
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    latestState = if (isConnected(context)) ONLINE else OFFLINE
    manager.registerNetworkCallback(networkRequest, this)
  }

  fun disable(context: Context) {
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    manager.unregisterNetworkCallback(this)
  }

  override fun onLost(network: Network) {
    if (latestState != null && latestState == ONLINE) {
      listener?.lostNetwork()
    }
    latestState = OFFLINE
  }

  override fun onAvailable(network: Network) {
    if (latestState != null && latestState == OFFLINE) {
      listener?.hasNetWork()
    }
    latestState = ONLINE
  }
}

interface NetworkListener {
  fun lostNetwork()
  fun hasNetWork()
}

enum class NetWorkState {
  OFFLINE,
  ONLINE
}