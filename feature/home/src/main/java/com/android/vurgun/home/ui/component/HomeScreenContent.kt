package com.android.vurgun.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.android.vurgun.home.HomeScreenContract
import com.android.vurgun.domain.model.SportUiModel
import com.android.vurgun.domain.model.SportGroupUiModel

@Composable
internal fun HomeScreenContent(
    uiState: HomeScreenContract.UiState,
    onSearchQueryChange: (String) -> Unit,
    onToggleGroupExpansion: (String) -> Unit,
    onSportClick: (SportUiModel) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            searchQuery = uiState.searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            placeholder = "Search sports..."
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
        ) {
            uiState.filteredSportGroup.forEach { sportGroup ->
                item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                    ExpandableGroupHeader(
                        groupName = sportGroup.groupName,
                        isExpanded = sportGroup.isExpanded,
                        onToggle = {
                            onToggleGroupExpansion(sportGroup.groupName)
                        }
                    )
                }

                if (sportGroup.isExpanded) {
                    items(sportGroup.sports) { sport ->
                        SportCard(
                            sport = sport,
                            onClick = onSportClick
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenContentWithDataPreview() {
    val sampleSports = listOf(
        SportUiModel(
            key = "basketball_nba",
            group = "Basketball",
            title = "NBA",
            description = "National Basketball Association",
            active = true,
            hasOutrights = true
        ),
        SportUiModel(
            key = "football_nfl",
            group = "Football",
            title = "NFL",
            description = "National Football League",
            active = true,
            hasOutrights = false
        )
    )

    val sampleGroups = listOf(
        SportGroupUiModel(
            groupName = "Basketball",
            sports = listOf(sampleSports[0]),
            isExpanded = true
        ),
        SportGroupUiModel(
            groupName = "Football",
            sports = listOf(sampleSports[1]),
            isExpanded = false
        )
    )

    HomeScreenContent(
        uiState = HomeScreenContract.UiState(
            isLoading = false,
            sportGroupUiModel = sampleGroups,
            filteredSportGroup = sampleGroups,
            searchQuery = ""
        ),
        onSearchQueryChange = {},
        onToggleGroupExpansion = {},
        onSportClick = {}
    )
}
