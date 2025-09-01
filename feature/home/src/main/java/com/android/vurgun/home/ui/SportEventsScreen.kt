package com.android.vurgun.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.vurgun.common_ui.component.LoadingView
import com.android.vurgun.common_ui.component.SnackBarType
import com.android.vurgun.common_ui.theme.LocalAppSharedViewModel
import com.android.vurgun.common_ui.theme.LocalAppSnackBarViewModel
import com.android.vurgun.common_ui.theme.LocalContentShimmerTheme
import com.android.vurgun.common_ui.viewmodel.SelectedBet
import com.android.vurgun.home.SportEventsScreenContract
import com.android.vurgun.home.SportEventsViewModel
import com.android.vurgun.home.common.LocalHomeContentShimmer
import com.android.vurgun.home.ui.component.SportEventsScreenContent
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SportEventsScreen(
    viewModel: SportEventsViewModel = hiltViewModel(),
    onEventClick: (String) -> Unit,
) {
    val appSnackBarViewModel = LocalAppSnackBarViewModel.current
    val appSharedViewModel = LocalAppSharedViewModel.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val bettingSlipState by appSharedViewModel.bettingSlipState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is SportEventsScreenContract.Event.ShowError -> {
                    appSnackBarViewModel.showSnackBar(
                        message = event.errorMessage,
                        requestedSnackBarType = SnackBarType.Error,
                        requestedSnackBarDuration = SnackbarDuration.Long,
                    )
                }
                is SportEventsScreenContract.Event.NavigateToEventDetail -> {
                    onEventClick(event.eventId)
                }
            }
        }
    }

    CompositionLocalProvider(
        LocalHomeContentShimmer provides rememberShimmer(
            shimmerBounds = ShimmerBounds.Window,
            theme = LocalContentShimmerTheme.current,
        ),
    ) {
        SportEventsScreenContent(
            uiState = state,
            onSearchQueryChange = viewModel::updateSearchQuery,
            onEventClick = { event ->
                onEventClick(event.id)
            },
            onSearchToggle = viewModel::toggleSearch,
            onOddsClick = { eventId, betType ->
                val event = state.eventsUiModel.find { it.id == eventId } ?: return@SportEventsScreenContent
                
                val odds = when (betType) {
                    "home" -> event.bookmakers.firstOrNull()?.markets?.firstOrNull()?.outcomes?.find { it.name == event.homeTeam }?.price ?: 1.73
                    "draw" -> event.bookmakers.firstOrNull()?.markets?.firstOrNull()?.outcomes?.find { it.name == "Draw" }?.price ?: 1.90
                    "away" -> event.bookmakers.firstOrNull()?.markets?.firstOrNull()?.outcomes?.find { it.name == event.awayTeam }?.price ?: 1.25
                    else -> 1.0
                }
                
                val selectedBet = SelectedBet(
                    eventId = eventId,
                    betType = betType,
                    odds = odds,
                    homeTeam = event.homeTeam,
                    awayTeam = event.awayTeam
                )
                
                appSharedViewModel.toggleBet(selectedBet)
            },
            selectedBets = bettingSlipState.selectedBets.mapValues { it.value.betType }
        )
    }
    
    AnimatedVisibility(
        visible = state.isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) { LoadingView() }
}