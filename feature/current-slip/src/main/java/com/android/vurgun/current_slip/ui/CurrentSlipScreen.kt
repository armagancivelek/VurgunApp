package com.android.vurgun.current_slip.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.vurgun.common_ui.R
import com.android.vurgun.common_ui.component.LoadingView
import com.android.vurgun.common_ui.component.SnackBarType
import com.android.vurgun.common_ui.theme.LocalAppSharedViewModel
import com.android.vurgun.common_ui.theme.LocalAppSnackBarViewModel
import com.android.vurgun.current_slip.ui.component.CurrentSlipScreenContent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CurrentSlipScreen(
    viewModel: CurrentSlipViewModel = hiltViewModel(),
) {
    val appSnackBarViewModel = LocalAppSnackBarViewModel.current
    val appSharedViewModel = LocalAppSharedViewModel.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val bettingSlipState by appSharedViewModel.bettingSlipState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is CurrentSlipContract.Event.ShowError -> {
                    appSnackBarViewModel.showSnackBar(
                        message = event.errorMessage,
                        requestedSnackBarType = SnackBarType.Error,
                        requestedSnackBarDuration = SnackbarDuration.Long,
                    )
                }
                is CurrentSlipContract.Event.ShowSuccess -> {
                    appSnackBarViewModel.showSnackBar(
                        message = event.message,
                        requestedSnackBarType = SnackBarType.Success,
                        requestedSnackBarDuration = SnackbarDuration.Long,
                    )
                }
            }
        }
    }

    if (state.isLoading) {
        LoadingView()
    } else {
        CurrentSlipScreenContent(
            bettingSlipState = bettingSlipState,
            onRemoveBet = { eventId -> appSharedViewModel.removeBet(eventId) },
            onClearAllBets = { appSharedViewModel.clearAllBets() },
            onSubmitBet = { betAmount ->
                appSharedViewModel.submitBet(betAmount)
                viewModel.showBetSubmittedSuccess()
            },
        )
    }
}