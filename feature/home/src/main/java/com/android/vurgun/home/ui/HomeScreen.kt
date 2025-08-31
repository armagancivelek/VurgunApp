package com.android.vurgun.home.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.vurgun.home.common.LocalHomeContentShimmer
import com.android.vurgun.home.ui.component.HomeScreenContent
import com.android.vurgun.common_ui.component.SnackBarType
import com.android.vurgun.common_ui.theme.LocalAppSnackBarViewModel
import com.android.vurgun.common_ui.theme.LocalContentShimmerTheme
import com.android.vurgun.common_ui.theme.WhiteColor
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClick: (String?) -> Unit,
) {
    val appSnackBarViewModel = LocalAppSnackBarViewModel.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { },
    )



    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is HomeScreenContract.Event.ShowError -> {
                    appSnackBarViewModel.showSnackBar(
                        message = event.errorMessage,
                        requestedSnackBarType = SnackBarType.Error,
                        requestedSnackBarDuration = SnackbarDuration.Long,
                    )
                }

                is HomeScreenContract.Event.NavigateToPhotoDetail -> onItemClick.invoke(event.photoId)
            }
        }
    }

    CompositionLocalProvider(
        LocalHomeContentShimmer provides rememberShimmer(
            shimmerBounds = ShimmerBounds.Window,
            theme = LocalContentShimmerTheme.current,
        ),
    ) {
         Surface(color = WhiteColor) {
             HomeScreenContent(
                 uiState = state,
                 viewModel = viewModel,
             )
         }

    }
}

