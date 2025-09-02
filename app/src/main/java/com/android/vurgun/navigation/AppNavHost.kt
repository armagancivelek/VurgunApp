package com.android.vurgun.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.android.vurgun.current_slip.navigation.currentSlipsScreen
import com.android.vurgun.home.navigation.homeScreen
import com.android.vurgun.home.navigation.navigateToSportEvents
import com.android.vurgun.slips.navigation.slipsScreen
import com.android.vurgun.ui.state.AppState

@ExperimentalLayoutApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = appState.navController,
        startDestination = appState.startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            onNavigateToSportEvents = { sportKey ->
                appState.navController.navigateToSportEvents(sportKey)
            },
        )

        slipsScreen()

        currentSlipsScreen()
    }
}