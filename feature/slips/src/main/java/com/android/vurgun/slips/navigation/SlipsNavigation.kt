package com.android.vurgun.slips.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.android.vurgun.slips.ui.SlipsScreen
import com.android.vurgun.common.route.AppRoute

fun NavController.navigateToSlips(navOptions: NavOptions) =
    navigate(route = AppRoute.SlipsRoute, navOptions)

fun NavGraphBuilder.slipsScreen(
    onItemClick: (String) -> Unit,
) {
    composable<AppRoute.SlipsRoute> {
        SlipsScreen(
            onItemClick = {}
        )
    }
}
