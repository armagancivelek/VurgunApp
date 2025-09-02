package com.android.vurgun.common.model

data class BettingSlipState(
    val selectedBets: Map<String, SelectedBet> = emptyMap(),
) {
    val totalMatches: Int get() = selectedBets.size
    val totalOdds: Double get() = if (selectedBets.isEmpty()) 0.0 else selectedBets.values.fold(1.0) { acc, bet -> acc * bet.odds }
}