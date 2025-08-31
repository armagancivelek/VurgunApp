package com.android.vurgun.ui.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.android.vurgun.MainViewModel
import com.android.vurgun.common.route.AppRoute
import com.android.vurgun.current_slip.navigation.navigateToCurrentSlip
import com.android.vurgun.home.navigation.navigateToHome
import com.android.vurgun.navigation.TopLevelDestination
import com.android.vurgun.network.common.NetworkConnectivityManager
import com.android.vurgun.slips.navigation.navigateToSlips
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = hiltViewModel(),
    networkConnectivityManager: NetworkConnectivityManager
): AppState {


    return remember(
        navController,
        coroutineScope,
    ) {
        AppState(
            navController = navController,
            coroutineScope = coroutineScope,
            networkConnectivityManager = networkConnectivityManager,
            startDestination = AppRoute.HomeRoute,
        )
    }
}


@Stable
class AppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    val startDestination: AppRoute,
    networkConnectivityManager: NetworkConnectivityManager
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            // Collect the currentBackStackEntryFlow as a state
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            // Fallback to previousDestination if currentEntry is null
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) == true
            }
        }

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.HOME -> {
                    navController.navigateToHome(topLevelNavOptions)
                }


                TopLevelDestination.SLIP -> {
                    navController.navigateToSlips(topLevelNavOptions)
                }

                TopLevelDestination.CURRENT_SLIP -> {
                    navController.navigateToCurrentSlip(topLevelNavOptions)
                }
            }
        }
    }

    val isOffline = networkConnectivityManager.getConnectivityStatusFlow()
        .map { it is NetworkConnectivityManager.ConnectivityStatus.Disconnected }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

}
