package com.android.vurgun.home.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.android.vurgun.common.route.AppRoute
import com.android.vurgun.home.ui.HomeScreen
import com.android.vurgun.home.ui.SportEventsScreen

fun NavController.navigateToHome(navOptions: NavOptions?) =
    navigate(route = AppRoute.HomeRoute, navOptions)

fun NavController.navigateToSportEvents(sportKey: String, navOptions: NavOptions? = null) =
    navigate(route = AppRoute.SportEventsRoute(sportKey), navOptions)

fun NavGraphBuilder.homeScreen(
    onItemClick: (String?) -> Unit,
    onNavigateToSportEvents: (String) -> Unit,
) {

    composable<AppRoute.HomeRoute>(
        enterTransition = { fadeIn(animationSpec = tween(1000)) },
        exitTransition = { fadeOut(animationSpec = tween(1000)) },
    ) {
        HomeScreen(
            onItemClick = onItemClick,
            onSportClick = onNavigateToSportEvents,
        )
    }

    composable<AppRoute.SportEventsRoute>(
        enterTransition = { fadeIn(animationSpec = tween(1000)) },
        exitTransition = { fadeOut(animationSpec = tween(1000)) },
    ) {
        SportEventsScreen(
            onEventClick = onItemClick,
            )
    }
}
