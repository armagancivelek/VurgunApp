package com.android.vurgun.home

import androidx.lifecycle.viewModelScope
import com.android.vurgun.analytics.FirebaseEventTracker
import com.android.vurgun.common.core.CoreViewModel
import com.android.vurgun.common_ui.R
import com.android.vurgun.common_ui.component.SnackBarType
import com.android.vurgun.domain.usecase.GetSportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSportsUseCase: GetSportsUseCase,
    private val firebaseEventTracker: FirebaseEventTracker,
) : CoreViewModel<HomeScreenContract.UiState, HomeScreenContract.Event>(
    initialState = HomeScreenContract.UiState(
        isLoading = false,
    ),
) {

    init {
        getSports()
    }

    private fun getSports() {
        viewModelScope.launch {
            getSportsUseCase(
                input = GetSportsUseCase.Params(all = true),
                onStart = {
                    updateState { it.copy(isLoading = true) }
                },
                onSuccess = { groupedSports ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            sportGroupUiModel = groupedSports,
                            filteredSportGroup = groupedSports,
                        )
                    }
                },
                onFailure = { exception ->
                    handleApiError(exception)
                },
            )
        }
    }

    fun updateSearchQuery(query: String) {
        val currentSportGroups = uiState.value.sportGroupUiModel
        val filteredGroups = if (query.isEmpty()) {
            currentSportGroups
        } else {
            currentSportGroups.mapNotNull { group ->
                val filteredSports = group.sports.filter { sport ->
                    sport.title.contains(query, ignoreCase = true) ||
                        sport.description.contains(query, ignoreCase = true) ||
                        sport.group.contains(query, ignoreCase = true)
                }
                if (filteredSports.isNotEmpty()) {
                    group.copy(sports = filteredSports)
                } else null
            }
        }

        updateState {
            it.copy(
                searchQuery = query,
                filteredSportGroup = filteredGroups,
            )
        }
    }

    fun toggleGroupExpansion(groupName: String) {
        val updatedSportGroups = uiState.value.sportGroupUiModel.map { group ->
            if (group.groupName == groupName) {
                group.copy(isExpanded = !group.isExpanded)
            } else {
                group
            }
        }

        val updatedFilteredGroups = uiState.value.filteredSportGroup.map { group ->
            if (group.groupName == groupName) {
                group.copy(isExpanded = !group.isExpanded)
            } else {
                group
            }
        }

        updateState {
            it.copy(
                sportGroupUiModel = updatedSportGroups,
                filteredSportGroup = updatedFilteredGroups,
            )
        }
    }

    fun sendSportEventDetail(sportKey: String) {
        firebaseEventTracker.sportDetails(
            params = mapOf(
                "sport_key" to sportKey,
            ),
        )
    }

    private fun handleApiError(exception: Throwable) {
        updateState { it.copy(isLoading = false) }
        sendEvent(
            HomeScreenContract.Event.ShowError(
                iconRes = R.drawable.ic_error,
                errorMessage = exception.message.orEmpty(),
                type = SnackBarType.Error,
            ),
        )
    }
}