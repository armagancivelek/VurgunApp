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

fun NavController.navigateToHome(navOptions: NavOptions?) =
    navigate(route = AppRoute.HomeRoute, navOptions)

fun NavGraphBuilder.homeScreen(
    onItemClick: (String?) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {

    composable<AppRoute.HomeRoute>(
        enterTransition = { fadeIn(animationSpec = tween(1000)) },
        exitTransition = { fadeOut(animationSpec = tween(1000)) },
    ) {
        HomeScreen(
            onItemClick = onItemClick,
        )
    }
}
