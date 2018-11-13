package com.tcorner.trappic.core.util

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * Created by Exequiel Egbert V. Ponce on 11/13/2018.
 */
/**
 * Injectable class which returns information about the network connection state.
 */
@Singleton
class NetworkUtil
@Inject constructor(private val context: Context) {
    val isConnected
        get() = {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            activeNetwork != null &&
                    (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                            activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
        }
}