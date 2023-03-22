package com.oneclick.blackandoneparent.core.network_helpers

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.oneclick.blackandoneparent.core.MyApplication

/**
 * Description: This class is used for the purpose of finding the network connectivity status.
 *
 * @author Ankit Mishra
 * @since 01/12/21
 *
 * @param application Provide the application context.
 */
class NetworkMonitor constructor(private val application: Application) {

    /**
     * Description: This method starts the connectivity observer.
     *
     * [NOTE: Call this method in the onStart() of the Application class. ]
     *
     * @author Ankit Mishra
     * @since 01/11/21
     */
    fun startNetworkCallback() {
        val cm: ConnectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()

        /**Check if version code is greater than API 24*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback)
        } else {
            cm.registerNetworkCallback(
                builder.build(), networkCallback
            )
        }
    }


    /**
     * Description: This method stops the connectivity observer.
     *
     * [NOTE: Call this method in the onTerminate() of the Application class.]
     *
     * @author Ankit Mishra
     * @since 01/11/21
     */
    fun stopNetworkCallback() {
        val cm: ConnectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
    }

    /**
     * This is network callback which will be called on any network change.
     */
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            MyApplication.isInternetAvailable = isNetworkAvailable()
        }

        override fun onLost(network: Network) {
            MyApplication.isInternetAvailable = isNetworkAvailable()
        }
    }

    /**
     * Description: This method is used for the purpose of checking if some network is available or not.
     *
     * @author Ankit Mishra
     * @since 01/11/21
     *
     * @return true if the network is available or false.
     */
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager: ConnectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }

    }
}