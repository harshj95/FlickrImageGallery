package com.application.flickr.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import com.application.flickr.data.model.Connection

/**
 * Created by Harsh Jain on 20/04/19.
 */

class ConnectionLiveData(context: Context) : LiveData<Connection>() {

    private val connectivityManager: ConnectivityManager = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network?) {
            postValue(Connection(true))
        }

        override fun onLost(network: Network?) {
            postValue(Connection(false))
        }
    }

    override fun onActive() {
        super.onActive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
        }
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}