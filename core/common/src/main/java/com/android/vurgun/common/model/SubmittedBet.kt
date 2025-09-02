package com.android.vurgun.common.model

data class SubmittedBet(
    val id: String,
    val bets: List<SelectedBet>,
    val betAmount: Double,
    val totalOdds: Double,
    val maxWin: Double,
    val submittedAt: Long = System.currentTimeMillis(),
)