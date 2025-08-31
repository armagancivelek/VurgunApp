package com.android.vurgun

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.android.vurgun.common_ui.screen.snackbar.AppSnackBarView
import com.android.vurgun.common_ui.screen.snackbar.AppSnackBarViewModel
import com.android.vurgun.common_ui.theme.BaseAppTheme
import com.android.vurgun.common_ui.theme.LocalAppSnackBarHostState
import com.android.vurgun.common_ui.theme.LocalAppSnackBarViewModel
import com.android.vurgun.common_ui.theme.LocalNetworkStatusFlow
import com.android.vurgun.ui.VurgunApp
import com.android.vurgun.ui.state.rememberAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val appSnackBarViewModel: AppSnackBarViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        lockPortraitForPhones()
        actionBar?.hide()
        enableEdgeToEdge()

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition {
            viewModel.uiState.value.keepSplashScreenOn
        }
        setContent {
            val appSnackBarHostState = remember { SnackbarHostState() }
            val appState = rememberAppState(
                networkConnectivityManager = viewModel.networkConnectivityManager,
            )

            CompositionLocalProvider(
                LocalAppSnackBarViewModel provides appSnackBarViewModel,
                LocalAppSnackBarHostState provides appSnackBarHostState,
                LocalNetworkStatusFlow provides appState.isOffline,
            ) {
                BaseAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        VurgunApp(
                            appState = appState,
                        )
                        AppSnackBarView()
                    }
                }
            }
        }
    }

    private fun lockPortraitForPhones() {
        requestedOrientation = if (!resources.getBoolean(R.bool.config_is_tablet)) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_SENSOR
        }
    }
}
