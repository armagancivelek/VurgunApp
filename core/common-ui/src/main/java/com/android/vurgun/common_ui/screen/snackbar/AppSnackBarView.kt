package com.android.vurgun.common_ui.screen.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.vurgun.common_ui.component.DefaultSnackBar
import com.android.vurgun.common_ui.theme.LocalAppSnackBarHostState
import com.android.vurgun.common_ui.theme.LocalAppSnackBarViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppSnackBarView(
    modifier: Modifier = Modifier,
) {
    val viewModel = LocalAppSnackBarViewModel.current
    val snackBarHostState = LocalAppSnackBarHostState.current

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is SnackBarEvent.Show -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration,
                    )
                }
            }
        }
    }
    AnimatedVisibility(
        visible = viewModel.isSnackBarVisible.value,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
        ) {
            DefaultSnackBar(
                snackBarHostState = snackBarHostState,
                type = viewModel.snackBarType.collectAsState(),
            )
        }
    }
}
