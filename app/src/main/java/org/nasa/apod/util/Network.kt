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

class Network(private val context: Context, val mListener: NetworkInterface) :
    LifecycleObserver {
    private val mLogTag: String = Network::class.java.simpleName
    private val mNetworkCallback: ConnectivityManager.NetworkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                mListener.isNetworkAvailable(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                mListener.isNetworkAvailable(false)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                mListener.isNetworkAvailable(false)
            }
        }
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun registerNetworkCallback() {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
        mListener.isNetworkAvailable(initializeFirstTimeValue())
    }

    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    private fun initializeFirstTimeValue(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            connectivityManager.activeNetworkInfo?.isConnected == true
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unregisterNetworkCallback() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(mNetworkCallback)
    }
}