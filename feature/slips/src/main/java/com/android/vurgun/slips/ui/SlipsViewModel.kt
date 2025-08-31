package com.android.vurgun.slips.ui

import com.android.vurgun.common.core.CoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SlipsViewModel @Inject constructor(

) : CoreViewModel<SlipsScreenContract.UiState, SlipsScreenContract.Event>(
    initialState = SlipsScreenContract.UiState(
        isLoading = false,
    ),
) {


}
