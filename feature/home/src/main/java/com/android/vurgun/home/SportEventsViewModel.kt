package com.android.vurgun.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.android.vurgun.common.core.CoreViewModel
import com.android.vurgun.common_ui.R
import com.android.vurgun.common_ui.component.SnackBarType
import com.android.vurgun.domain.model.OddsUiModel
import com.android.vurgun.domain.usecase.GetOddsUseCase
import com.android.vurgun.network.common.NetworkConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportEventsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val networkConnectivityManager: NetworkConnectivityManager,
    private val getOddsUseCase: GetOddsUseCase,
) : CoreViewModel<SportEventsScreenContract.UiState, SportEventsScreenContract.Event>(
    initialState = SportEventsScreenContract.UiState(
        isLoading = false,
    ),
) {

    private val sportKey: String = savedStateHandle.get<String>("sportKey") ?: ""

    init {
    //    observeNetworkConnectivity()
        if (sportKey.isNotEmpty()) {
            getEvents()
        }
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
                            // Handle disconnection
                        }
                    }
                }
        }
    }

    private fun getEvents() {
        viewModelScope.launch {
            getOddsUseCase(
                input = GetOddsUseCase.Params(
                    sport = sportKey,
                    regions = "us,uk,eu",
                    markets = "h2h",
                    dateFormat = "iso",
                    oddsFormat = "decimal"
                ),
                onStart = {
                    updateState { it.copy(isLoading = true) }
                },
                onSuccess = { events ->
                    updateEventsData(events)
                },
                onFailure = { exception ->
                    handleApiError(exception)
                }
            )
        }
    }

    fun updateSearchQuery(query: String) {
        val currentEvents = uiState.value.eventsUiModel
        val filteredEvents = if (query.isEmpty()) {
            currentEvents
        } else {
            currentEvents.filter { event ->
                event.homeTeam.contains(query, ignoreCase = true) ||
                event.awayTeam.contains(query, ignoreCase = true) ||
                event.sportTitle.contains(query, ignoreCase = true)
            }
        }

        updateState {
            it.copy(
                searchQuery = query,
                filteredEventsUiModel = filteredEvents
            )
        }
    }

    fun toggleSearch() {
        updateState {
            it.copy(
                isSearchVisible = !it.isSearchVisible,
                searchQuery = if (!it.isSearchVisible) "" else it.searchQuery
            )
        }
        if (!uiState.value.isSearchVisible) {
            updateSearchQuery("")
        }
    }

    private fun updateEventsData(events: List<OddsUiModel>) {
        val sportTitle = events.firstOrNull()?.sportTitle ?: ""
        updateState {
            it.copy(
                isLoading = false,
                eventsUiModel = events,
                filteredEventsUiModel = events,
                sportKey = sportKey,
                sportTitle = sportTitle
            )
        }
    }

    private fun handleApiError(exception: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEvent(
            SportEventsScreenContract.Event.ShowError(
                iconRes = R.drawable.ic_error,
                errorMessage = exception.message.orEmpty(),
                type = SnackBarType.Error
            )
        )
    }

    override fun retry() {
        if (sportKey.isNotEmpty()) {
            getEvents()
        }
    }
}