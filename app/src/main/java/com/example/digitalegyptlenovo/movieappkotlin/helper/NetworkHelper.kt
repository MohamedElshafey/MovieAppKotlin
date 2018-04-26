package com.example.digitalegyptlenovo.movieappkotlin.helper

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Mohamed Elshafey on 4/25/2018.
 */
class NetworkHelper {

    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}