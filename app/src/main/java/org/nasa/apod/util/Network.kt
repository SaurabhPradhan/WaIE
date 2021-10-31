package org.nasa.apod.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class Network(private val context: Context, val networkInterface: NetworkInterface) {

    private val mNetworkCallback: ConnectivityManager.NetworkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkInterface.isNetworkAvailable(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                networkInterface.isNetworkAvailable(false)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                networkInterface.isNetworkAvailable(false)
            }
        }
    }

    fun registerNetworkCallback() {
        try {
            val connectivityManager =
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(mNetworkCallback)
            } else {
                connectivityManager.registerNetworkCallback(
                    NetworkRequest.Builder().build(),
                    mNetworkCallback
                )
            }
        } catch (e: Exception) {
            Log.e("Network error", e.toString())
        }
        if (!isNetworkAvailable(context)) {
            networkInterface.isNetworkAvailable(false)
        }
    }

    fun unregisterNetworkCallback() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(mNetworkCallback)
    }

    @Suppress("DEPRECATION")
    private fun isNetworkAvailable(context: Context): Boolean {
        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }
}