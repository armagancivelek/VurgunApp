package com.android.vurgun.common_ui.screen.snackbar

import androidx.compose.material3.SnackbarDuration
import com.android.vurgun.common_ui.component.SnackBarType

sealed interface SnackBarEvent {
    data class Show(
        val message: String,
        val type: SnackBarType,
        val duration: SnackbarDuration,
    ) : SnackBarEvent
}
