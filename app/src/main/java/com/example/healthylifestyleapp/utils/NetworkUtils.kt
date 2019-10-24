package com.example.healthylifestyleapp.utils

import android.content.Context
import android.net.ConnectivityManager

fun isNetworkAccessible(context: Context): Boolean {
    val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (connectivityManager.activeNetworkInfo != null) {
        (connectivityManager.activeNetworkInfo?.isConnected as Boolean)
    } else {
        false
    }
}
