package com.android.vurgun.network.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

interface NetworkConnectivityManager {

    val isConnected: Boolean
    fun getConnectivityStatusFlow(): Flow<ConnectivityStatus>

    sealed interface ConnectivityStatus {
        data object Connected : ConnectivityStatus
        data object Disconnected : ConnectivityStatus
    }
}

class NetworkConnectivityManagerImpl(
    context: Context,
) : NetworkConnectivityManager {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val isConnected: Boolean
        get() = connectivityManager.activeNetwork != null

    override fun getConnectivityStatusFlow(): Flow<NetworkConnectivityManager.ConnectivityStatus> = callbackFlow {
        send(if (isConnected) NetworkConnectivityManager.ConnectivityStatus.Connected else NetworkConnectivityManager.ConnectivityStatus.Disconnected)

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                launch { send(NetworkConnectivityManager.ConnectivityStatus.Connected) }
            }

            override fun onLost(network: Network) {
                launch { send(NetworkConnectivityManager.ConnectivityStatus.Disconnected) }
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
}