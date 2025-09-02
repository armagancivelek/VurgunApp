package com.android.vurgun.current_slip.ui

import com.android.vurgun.analytics.FirebaseEventTracker
import com.android.vurgun.common.core.CoreViewModel
import com.android.vurgun.common.stringprovider.StringProvider
import com.android.vurgun.common_ui.component.SnackBarType
import com.android.vurgun.common_ui.R as R_common_ui
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrentSlipViewModel @Inject constructor(
    private val stringProvider: StringProvider
) : CoreViewModel<CurrentSlipContract.UiState, CurrentSlipContract.Event>(
    initialState = CurrentSlipContract.UiState(
        isLoading = false,
    ),
) {
    fun showBetSubmittedSuccess() {
        sendEvent(
            CurrentSlipContract.Event.ShowSuccess(
                message = stringProvider.getString(R_common_ui.string.bet_submitted_success),
            ),
        )
    }

    fun checkBalanceIsEnough(balance: Double, betAmount: Double) : Boolean{
        if (balance < betAmount) {
            sendEvent(
                CurrentSlipContract.Event.ShowError(
                    iconRes = R_common_ui.drawable.ic_error,
                    errorMessage = stringProvider.getString(R_common_ui.string.insufficient_balance),
                    type = SnackBarType.Error,
                ),
            )
            return false
        }
        return true
    }
}