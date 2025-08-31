package com.android.vurgun.navigation

import androidx.annotation.StringRes
import com.android.vurgun.R
import com.android.vurgun.common.route.AppRoute
import com.android.vurgun.common_ui.icon.AppIcons
import kotlin.reflect.KClass
import com.android.vurgun.common_ui.R.string as R_common

/**
 * Type for the top level destinations in the application. Contains metadata about the destination
 * that is used in the top app bar and common navigation UI.
 *
 * @param selectedIcon The icon to be displayed in the navigation UI when this destination is
 * selected.
 * @param iconTextId Text that to be displayed in the navigation UI.
 * @param titleTextId Text that is displayed on the top app bar.
 * @param route The route to use when navigating to this destination.
 */
enum class TopLevelDestination(
    val icon: Int,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
) {
    HOME(
        icon = AppIcons.Home,
        iconTextId = R_common.feature_home_title,
        titleTextId = R_common.feature_home_title,
        route = AppRoute.HomeRoute::class,
    ),
    CURRENT_SLIP(
        icon = AppIcons.Slips,
        iconTextId = R_common.feature_current_slip_title,
        titleTextId = R_common.feature_current_slip_title,
        route = AppRoute.CurrentSlipRoute::class,
    ),
    SLIP(
        icon = AppIcons.Slips,
        iconTextId = R_common.feature_slip_title,
        titleTextId = R_common.feature_slip_title,
        route = AppRoute.SlipsRoute::class,
    ),



}
