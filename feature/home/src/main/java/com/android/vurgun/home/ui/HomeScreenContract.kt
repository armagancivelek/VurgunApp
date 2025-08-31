package com.android.vurgun.home.ui

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.android.vurgun.common.core.CoreState
import com.android.vurgun.common_ui.component.SnackBarType

class HomeScreenContract {

    @Immutable
    data class UiState(
        override val isLoading: Boolean,
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
