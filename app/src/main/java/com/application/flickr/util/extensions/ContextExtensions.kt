package com.application.flickr.util.extensions

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Harsh Jain on 20/04/19.
 */

fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    return activeNetwork?.isConnected == true
}