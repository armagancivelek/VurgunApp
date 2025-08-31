package com.android.vurgun.home.ui

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.android.vurgun.common.core.CoreState
import com.android.vurgun.common_ui.component.SnackBarType
import com.android.vurgun.domain.model.ScoresUiModel
import com.android.vurgun.domain.model.SportUiModel

class HomeScreenContract {

    @Immutable
    data class UiState(
        override val isLoading: Boolean,
        val sports: List<SportUiModel> = emptyList(),
        val scores: List<ScoresUiModel> = emptyList(),
    ) : CoreState.ViewState

    sealed class Event : CoreState.Event {
        data class NavigateToPhotoDetail(
            val photoId: String?,
        ) : Event()

        data class ShowError(
            @DrawableRes val iconRes: Int,
            val errorMessage: String,
            val type: SnackBarType,
        ) : Event()
    }

}
