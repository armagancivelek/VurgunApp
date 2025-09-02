package com.android.vurgun.common_ui.theme

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import com.android.vurgun.common_ui.screen.snackbar.AppSnackBarViewModel
import com.android.vurgun.common.viewmodel.AppSharedViewModel
import kotlinx.coroutines.flow.StateFlow

val LocalAppSnackBarViewModel: ProvidableCompositionLocal<AppSnackBarViewModel> = provideCompositionLocal()
val LocalAppSnackBarHostState: ProvidableCompositionLocal<SnackbarHostState> = provideCompositionLocal()
val LocalNetworkStatusFlow: ProvidableCompositionLocal<StateFlow<Boolean>> = provideCompositionLocal()
val LocalAppSharedViewModel: ProvidableCompositionLocal<AppSharedViewModel> = provideCompositionLocal()

private inline fun <reified C> provideCompositionLocal() = compositionLocalOf<C> {
    error("No ${C::class} provided")
}