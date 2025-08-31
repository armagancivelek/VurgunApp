package com.android.vurgun.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.android.vurgun.common_ui.component.LoadingView
import com.android.vurgun.home.ui.HomeScreenContract
import com.android.vurgun.home.ui.HomeViewModel

@Composable
internal fun HomeScreenContent(
    uiState: HomeScreenContract.UiState,
    viewModel: HomeViewModel,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (uiState.isLoading) {
            LoadingView(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SearchBar(
                    searchQuery = uiState.searchQuery,
                    onSearchQueryChange = { query ->
                        viewModel.onEvent(HomeScreenContract.Event.UpdateSearchQuery(query))
                    },
                    placeholder = "Search sports..."
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
                ) {
                    uiState.filteredSportGroups.forEach { sportGroup ->
                        item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                            Text(
                                text = sportGroup.groupName.uppercase(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 4.dp,
                                        top = 24.dp,
                                        bottom = 12.dp
                                    )
                            )
                        }

                        items(sportGroup.sports) { sport ->
                            SportCard(
                                sport = sport,
                                onClick = { selectedSport ->
                                    // Handle sport selection
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
