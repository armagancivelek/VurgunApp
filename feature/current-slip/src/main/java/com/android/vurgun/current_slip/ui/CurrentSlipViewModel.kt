package com.android.vurgun.current_slip.ui

import com.android.vurgun.common.core.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrentSlipViewModel @Inject constructor() : CoreViewModel<CurrentSlipContract.UiState, CurrentSlipContract.Event>(
    initialState = CurrentSlipContract.UiState(
        isLoading = false,
    ),
)