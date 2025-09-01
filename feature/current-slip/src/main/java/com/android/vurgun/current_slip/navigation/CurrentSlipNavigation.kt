package com.android.vurgun.current_slip.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.android.vurgun.current_slip.ui.CurrentSlipScreen
import com.android.vurgun.common.route.AppRoute

fun NavController.navigateToCurrentSlip(navOptions: NavOptions) =
    navigate(route = AppRoute.CurrentSlipRoute, navOptions)

fun NavGraphBuilder.currentSlipsScreen(
    onItemClick: (String) -> Unit
) {
    composable<AppRoute.CurrentSlipRoute> {
        CurrentSlipScreen(
            onItemClick = {}
        )
    }
}
