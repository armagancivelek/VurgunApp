package com.android.vurgun.common.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class CoreViewModel<STATE : CoreState.ViewState, EVENT : CoreState.Event>(
    private val initialState: STATE,
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<STATE> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<EVENT>()
    val event = _event.asSharedFlow()

    fun updateState(newState: (STATE) -> STATE) {
        if (newState(uiState.value) != _uiState.value) {
            _uiState.value = newState(uiState.value)
        }
    }

    fun sendEvent(event: EVENT) {
        viewModelScope.launch { _event.emit(event) }
    }
}