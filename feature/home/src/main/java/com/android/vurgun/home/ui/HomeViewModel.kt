package com.android.vurgun.home.ui

import androidx.lifecycle.viewModelScope
import com.android.vurgun.common.core.CoreViewModel
import com.android.vurgun.common_ui.component.SnackBarType
import com.android.vurgun.domain.usecase.GetScoresUseCase
import com.android.vurgun.domain.usecase.GetSportsUseCase
import com.android.vurgun.network.common.NetworkConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkConnectivityManager: NetworkConnectivityManager,
    private val getScoresUseCase: GetScoresUseCase,
    private val getSportsUseCase: GetSportsUseCase,
) : CoreViewModel<HomeScreenContract.UiState, HomeScreenContract.Event>(
    initialState = HomeScreenContract.UiState(
        isLoading = false,
    ),
) {

    init {
        observeNetworkConnectivity()
        getSports()
      //  getScores()
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


    private fun getScores() {
        viewModelScope.launch {
            getScoresUseCase(
                input = GetScoresUseCase.Params(
                    sport = "basketball_nba",
                    daysFrom = 3,
                    dateFormat = "iso"
                ),
                onStart = {
                    updateState { it.copy(isLoading = true) }
                },
                onSuccess = { scores ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            scores = scores
                        )
                    }
                },
                onFailure = { exception ->
                    updateState {
                        it.copy(isLoading = false)
                    }
                    sendEvent(
                        HomeScreenContract.Event.ShowError(
                            iconRes = com.android.vurgun.common_ui.R.drawable.ic_error,
                            errorMessage = exception.message.orEmpty(),
                            type = SnackBarType.Error
                        )
                    )
                }
            )
        }
    }

    private fun getSports() {
        viewModelScope.launch {
            getSportsUseCase(
                input = GetSportsUseCase.Params(all = true),
                onStart = {
                    updateState { it.copy(isLoading = true) }
                },
                onSuccess = { sports ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            sports = sports
                        )
                    }
                },
                onFailure = { exception ->
                    updateState {
                        it.copy(isLoading = false)
                    }
                    sendEvent(
                        HomeScreenContract.Event.ShowError(
                            iconRes = com.android.vurgun.common_ui.R.drawable.ic_error,
                            errorMessage = exception.message.orEmpty(),
                            type = SnackBarType.Error
                        )
                    )
                }
            )
        }
    }

    override fun retry() {
        getSports()
        getScores()
    }


}
