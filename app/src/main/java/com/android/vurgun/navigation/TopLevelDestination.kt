package com.android.vurgun.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
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
 * @param unselectedIcon The icon to be displayed in the navigation UI when this destination is
 * not selected.
 * @param iconTextId Text that to be displayed in the navigation UI.
 * @param titleTextId Text that is displayed on the top app bar.
 * @param route The route to use when navigating to this destination.
 * @param baseRoute The highest ancestor of this destination. Defaults to [route], meaning that
 * there is a single destination in that section of the app (no nested destinations).
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    val route: KClass<*>,
    val baseRoute: KClass<*> = route,
) {
    HOME(
        selectedIcon = AppIcons.Upcoming,
        unselectedIcon = AppIcons.UpcomingBorder,
        iconTextId = R_common.feature_home_title,
        titleTextId = R.string.app_name,
        route = AppRoute.HomeRoute::class,
        baseRoute = AppRoute.HomeRoute::class,
    ),

}
