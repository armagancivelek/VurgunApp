package com.android.vurgun.current_slip.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.vurgun.common_ui.component.LoadingView
import com.android.vurgun.common_ui.component.SnackBarType
import com.android.vurgun.common_ui.theme.LocalAppSnackBarViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CurrentSlipScreen(
    viewModel: CurrentSlipViewModel = hiltViewModel(),
    onItemClick: (String?) -> Unit,
) {
    val appSnackBarViewModel = LocalAppSnackBarViewModel.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()


    val lifecycleOwner = LocalLifecycleOwner.current
    Text("CurrentSlip")





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

                is CurrentSlipContract.Event.NavigateToPhotoDetail -> TODO()
                else -> {}
            }
        }
    }

    if (state.isLoading) {
        LoadingView()
    } else {

    }
}
