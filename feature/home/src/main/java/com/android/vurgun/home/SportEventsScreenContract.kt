package com.android.vurgun.home

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.android.vurgun.common.core.CoreState
import com.android.vurgun.common_ui.component.SnackBarType
import com.android.vurgun.domain.model.OddsUiModel

class SportEventsScreenContract {

    @Immutable
    data class UiState(
        override val isLoading: Boolean,
        val eventsUiModel: List<OddsUiModel> = emptyList(),
        val filteredEventsUiModel: List<OddsUiModel> = emptyList(),
        val searchQuery: String = "",
        val sportKey: String = "",
        val sportTitle: String = "",
        val isSearchVisible: Boolean = false,
        val selectedBets: Map<String, String> = emptyMap(),
    ) : CoreState.ViewState

    sealed class Event : CoreState.Event {
        data class ShowError(
            @DrawableRes val iconRes: Int,
            val errorMessage: String,
            val type: SnackBarType,
        ) : Event()
    }
}