package com.android.vurgun.home.ui

import androidx.lifecycle.viewModelScope
import com.android.vurgun.common.core.CoreViewModel
import com.android.vurgun.network.common.NetworkConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkConnectivityManager: NetworkConnectivityManager,
) : CoreViewModel<HomeScreenContract.UiState, HomeScreenContract.Event>(
    initialState = HomeScreenContract.UiState(
        isLoading = false,
    ),
) {

    init {
        observeNetworkConnectivity()
    }

    private fun observeNetworkConnectivity() {
        viewModelScope.launch {
            networkConnectivityManager.getConnectivityStatusFlow()
                .collectLatest { status ->
                    when (status) {
                        is NetworkConnectivityManager.ConnectivityStatus.Connected -> {
                            retry()
                        }

                        is NetworkConnectivityManager.ConnectivityStatus.Disconnected -> {

                        }
                    }
                }
        }
    }







    override fun retry() {

    }


}
