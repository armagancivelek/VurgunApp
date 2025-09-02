package com.android.vurgun.current_slip.ui.component

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import com.android.vurgun.common_ui.theme.GreenColor
import com.android.vurgun.common_ui.theme.RemoveColor
import com.android.vurgun.common_ui.theme.WhiteColor
import com.android.vurgun.common_ui.util.getBetTypeDisplayName
import com.android.vurgun.common.model.BettingSlipState
import com.android.vurgun.common.model.SelectedBet
import com.android.vurgun.common_ui.R as R_common_ui

@Composable
internal fun CurrentSlipScreenContent(
    bettingSlipState: BettingSlipState,
    onRemoveBet: (String) -> Unit,
    onClearAllBets: () -> Unit,
    onSubmitBet: (Double) -> Unit,
) {
    var betAmount by remember { mutableStateOf("") }

    val maxWin by remember(bettingSlipState.totalOdds, betAmount) {
        derivedStateOf {
            val bet = betAmount.toDoubleOrNull() ?: 0.0
            bettingSlipState.totalOdds * bet
        }
    }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(WhiteColor),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.bet_amount_label),
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                OutlinedTextField(
                    value = betAmount,
                    onValueChange = {
                        if (betAmount.length < 5) {
                            betAmount = it.filter { char -> char.isDigit() }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(R.string.bet_amount_placeholder)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlueColor,
                        unfocusedBorderColor = Color.Gray,
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                    ),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = stringResource(R.string.bet_max_win_label) + ":",
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    Text(
                        text = "$maxWin",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Button(
                        onClick = {
                            onClearAllBets()
                            betAmount = ""
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RemoveColor,
                        ),
                    ) {
                        Text(
                            text = stringResource(R.string.clear_all_bets),
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                    }

                    Button(
                        onClick = {
                            val amount = betAmount.toDoubleOrNull() ?: 0.0
                            if (amount > 0 && bettingSlipState.selectedBets.isNotEmpty()) {
                                onSubmitBet(amount)
                                betAmount = ""
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = betAmount.toDoubleOrNull() != null && betAmount.toDoubleOrNull()!! > 0 && bettingSlipState.selectedBets.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenColor,
                        ),
                    ) {
                        Text(
                            text = stringResource(R.string.place_bet),
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGrayColor)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.current_slip_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Text(
                    text = stringResource(R.string.match_count, bettingSlipState.totalMatches),
                    fontSize = 14.sp,
                    color = Color.Gray,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (bettingSlipState.selectedBets.values.isEmpty()) {
                Box(Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            painter = painterResource(id = R_common_ui.drawable.ic_warning),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(92.dp).align(Alignment.CenterHorizontally),
                        )
                        Text(
                            text = stringResource(R.string.empty_betting_slip),
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 16.dp),
                ) {
                    items(bettingSlipState.selectedBets.values.toList()) { bet ->
                        BetCard(
                            bet = bet,
                            onRemove = { onRemoveBet(bet.eventId) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BetCard(
    bet: SelectedBet,
    onRemove: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
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
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                BlueColor,
                                RoundedCornerShape(4.dp),
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                    ) {
                        Text(
                            text = bet.odds.toString(),
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = RemoveColor,
                                RoundedCornerShape(12.dp),
                            )
                            .clickable { onRemove() },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "×",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CurrentSlipScreenContentPreview() {
    BaseAppTheme {
        CurrentSlipScreenContent(
            bettingSlipState = BettingSlipState(
                selectedBets = mapOf(
                    "1" to SelectedBet(
                        eventId = "1",
                        betType = "MS 1",
                        odds = 1.90,
                        homeTeam = "Galatasaray",
                        awayTeam = "Çaykur Rizespor",
                    ),
                    "2" to SelectedBet(
                        eventId = "2",
                        betType = "MS 2",
                        odds = 1.25,
                        homeTeam = "Fenerbahçe",
                        awayTeam = "Beşiktaş",
                    ),
                ),
            ),
            onRemoveBet = {},
            onClearAllBets = {},
            onSubmitBet = { _ -> },
        )
    }
}