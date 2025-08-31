package com.android.vurgun

import androidx.lifecycle.viewModelScope
import com.android.vurgun.common.core.CoreViewModel
import com.android.vurgun.data.data.DataStoreRepository
import com.android.vurgun.network.common.NetworkConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository, val networkConnectivityManager: NetworkConnectivityManager) : CoreViewModel<MainContract.State, MainContract.Event>(
    initialState = MainContract.State(
        isLoading = false,
    ),
) {
    init {
        viewModelScope.launch {
            delay(MainContract.Static.KEEP_SPLASH_SCREEN_DELAY)
            updateState { it.copy(keepSplashScreenOn = false) }
        }
    }

}
