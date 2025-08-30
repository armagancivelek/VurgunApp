package com.android.vurgun.common_ui.screen.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.vurgun.common_ui.component.SnackBarType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSnackBarViewModel @Inject constructor() : ViewModel() {

    private val _isSnackBarVisible = mutableStateOf(false)
    internal val isSnackBarVisible = _isSnackBarVisible

    private val _snackBarType: MutableStateFlow<SnackBarType> = MutableStateFlow(SnackBarType.Error)
    val snackBarType = _snackBarType.asStateFlow()

    private val _event = Channel<SnackBarEvent>()
    internal val event = _event.receiveAsFlow()

    private var snackBarJob: Job? = null

    fun showSnackBar(
        message: String,
        requestedSnackBarType: SnackBarType = SnackBarType.Error,
        requestedSnackBarDuration: SnackbarDuration = SnackbarDuration.Short,
    ) {
        if (snackBarJob?.isActive == true) {
            snackBarJob?.invokeOnCompletion {
                snackBarJob = null
                viewModelScope.launch {
                    showSnackBar(
                        message,
                        requestedSnackBarType,
                        requestedSnackBarDuration,
                    )
                }
            }
            return
        }
        snackBarJob?.cancel()
        snackBarJob = viewModelScope.launch {
            _isSnackBarVisible.value = true
            _snackBarType.value = requestedSnackBarType
            _event.trySend(
                SnackBarEvent.Show(
                    message = message,
                    type = requestedSnackBarType,
                    duration = requestedSnackBarDuration,
                ),
            )
            delay(3_000L)
            forceHide()
        }
    }

    private fun forceHide() {
        snackBarJob?.cancel()
        _isSnackBarVisible.value = false
    }
}
