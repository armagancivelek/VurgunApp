package com.android.vurgun.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.vurgun.common_ui.R
import com.android.vurgun.domain.model.OddsUiModel
import com.android.vurgun.home.SportEventsScreenContract

@Composable
internal fun SportEventsScreenContent(
    uiState: SportEventsScreenContract.UiState,
    onSearchQueryChange: (String) -> Unit,
    onEventClick: (OddsUiModel) -> Unit,
    onSearchToggle: () -> Unit,
    onOddsClick: (String, String) -> Unit,
    selectedBets: Map<String, String> = emptyMap(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        if (uiState.isSearchVisible) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SearchBar(
                    searchQuery = uiState.searchQuery,
                    onSearchQueryChange = onSearchQueryChange,
                    modifier = Modifier.weight(1f),
                    placeholder = stringResource(R.string.search_competition_placeholder),
                )
                IconButton(onClick = onSearchToggle) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = uiState.sportTitle.ifEmpty { "Sport Events" },
                    style = MaterialTheme.typography.titleLarge,
                )
                IconButton(onClick = onSearchToggle) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.filteredEventsUiModel.isEmpty() && !uiState.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "⚠️",
                    style = MaterialTheme.typography.headlineLarge,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.no_sport_details_available),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 20.dp),
            ) {
                items(uiState.filteredEventsUiModel) { event ->
                    EventCard(
                        event = event,
                        onClick = onEventClick,
                        selectedBetType = selectedBets[event.id],
                        onOddsClick = onOddsClick,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SportEventsScreenContentPreview() {
    val sampleEvents = listOf(
        OddsUiModel(
            id = "1",
            sportKey = "basketball_nba",
            sportTitle = "NBA",
            commenceTime = "2024-01-15T20:00:00Z",
            homeTeam = "Lakers",
            awayTeam = "Warriors",
            bookmakers = emptyList(),
        ),
        OddsUiModel(
            id = "2",
            sportKey = "basketball_nba",
            sportTitle = "NBA",
            commenceTime = "2024-01-15T22:00:00Z",
            homeTeam = "Celtics",
            awayTeam = "Heat",
            bookmakers = emptyList(),
        ),
    )

    SportEventsScreenContent(
        uiState = SportEventsScreenContract.UiState(
            isLoading = false,
            eventsUiModel = sampleEvents,
            filteredEventsUiModel = sampleEvents,
            searchQuery = "",
            sportKey = "basketball_nba",
        ),
        onSearchQueryChange = {},
        onEventClick = {},
        onSearchToggle = {},
        onOddsClick = { _, _ -> },
    )
}