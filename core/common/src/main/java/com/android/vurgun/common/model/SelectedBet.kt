package com.android.vurgun.common.model

data class SelectedBet(
    val eventId: String,
    val betType: String,
    val odds: Double,
    val homeTeam: String,
    val awayTeam: String,
)