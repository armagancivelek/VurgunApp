package com.android.vurgun.slips.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.vurgun.common_ui.component.LoadingView
import com.android.vurgun.common_ui.component.SnackBarType
import com.android.vurgun.common_ui.theme.LocalAppSharedViewModel
import com.android.vurgun.common_ui.theme.LocalAppSnackBarViewModel
import com.android.vurgun.slips.ui.component.SlipsScreenContent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SlipsScreen(
    viewModel: SlipsViewModel = hiltViewModel(),
) {
    val appSnackBarViewModel = LocalAppSnackBarViewModel.current
    val appSharedViewModel = LocalAppSharedViewModel.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val submittedBets by appSharedViewModel.submittedBets.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is SlipsScreenContract.Event.ShowError -> {
                    appSnackBarViewModel.showSnackBar(
                        message = event.errorMessage,
                        requestedSnackBarType = SnackBarType.Error,
                        requestedSnackBarDuration = SnackbarDuration.Long,
                    )
                }
            }
        }
    }
    SlipsScreenContent(
        submittedBets = submittedBets,
    )
    AnimatedVisibility(
        visible = state.isLoading,
        enter = fadeIn(),
        exit = fadeOut(),
    ) { LoadingView() }
}