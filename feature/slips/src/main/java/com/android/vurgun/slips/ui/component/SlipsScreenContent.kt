package com.android.vurgun.slips.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.vurgun.common_ui.R
import com.android.vurgun.common_ui.theme.BaseAppTheme
import com.android.vurgun.common_ui.theme.BackgroundGrayColor
import com.android.vurgun.common_ui.theme.BlueColor
import com.android.vurgun.common_ui.theme.CardBackgroundColor
import com.android.vurgun.common_ui.theme.WhiteColor
import com.android.vurgun.common_ui.util.getBetTypeDisplayName
import com.android.vurgun.common_ui.viewmodel.SelectedBet
import com.android.vurgun.common_ui.viewmodel.SubmittedBet
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
internal fun SlipsScreenContent(
    submittedBets: List<SubmittedBet>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGrayColor)
            .padding(16.dp),
    ) {
        if (submittedBets.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_warning),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.no_bets_played_yet),
                        fontSize = 16.sp,
                        color = Color.Gray,
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
            ) {
                items(submittedBets.reversed()) { submittedBet ->
                    ExpandableSlipCard(submittedBet = submittedBet)
                }
            }
        }
    }
}

@Composable
private fun ExpandableSlipCard(
    submittedBet: SubmittedBet,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(containerColor = WhiteColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.slip_number, submittedBet.id.takeLast(4)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )

                Text(
                    text = if (isExpanded) "▲" else "▼",
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .background(BlueColor, RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.matches_label, submittedBet.bets.size),
                            color = WhiteColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }

                    Text(
                        text = String.format("%.2f", submittedBet.totalOdds),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )

                    Text(
                        text = stringResource(R.string.odds_label),
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = "${String.format("%.0f", submittedBet.betAmount)} TL",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                    )
                    Text(
                        text = stringResource(R.string.bet_amount_short),
                        fontSize = 10.sp,
                        color = Color.Gray,
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = String.format("%.2f", submittedBet.maxWin),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                    Text(
                        text = stringResource(R.string.max_win_short),
                        fontSize = 10.sp,
                        color = Color.Gray,
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically(),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))

                    submittedBet.bets.forEach { bet ->
                        SubmittedBetCard(bet = bet)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.played_date, formatDate(submittedBet.submittedAt)),
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                }
            }
        }
    }
}

@Composable
private fun SubmittedBetCard(
    bet: SelectedBet,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(6.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${bet.homeTeam} - ${bet.awayTeam}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                )
                Text(
                    text = getBetTypeDisplayName(bet.betType),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 2.dp),
                )
            }

            Box(
                modifier = Modifier
                    .background(BlueColor, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
                Text(
                    text = bet.odds.toString(),
                    color = WhiteColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return formatter.format(Date(timestamp))
}

@Preview
@Composable
private fun SlipsScreenContentPreview() {
    BaseAppTheme {
        SlipsScreenContent(
            submittedBets = listOf(
                SubmittedBet(
                    id = "BET_1234567890",
                    bets = listOf(
                        SelectedBet(
                            eventId = "1",
                            betType = "MS 1",
                            odds = 1.90,
                            homeTeam = "Galatasaray",
                            awayTeam = "Çaykur Rizespor",
                        ),
                        SelectedBet(
                            eventId = "2",
                            betType = "MS 2",
                            odds = 1.25,
                            homeTeam = "Fenerbahçe",
                            awayTeam = "Beşiktaş",
                        ),
                    ),
                    betAmount = 50.0,
                    totalOdds = 2.375,
                    maxWin = 118.75,
                ),
            ),
        )
    }
}