package com.android.vurgun.domain.model

data class ScoresUiModel(
    val id: String,
    val sportKey: String,
    val sportTitle: String,
    val commenceTime: String,
    val completed: Boolean,
    val homeTeam: String,
    val awayTeam: String,
    val scores: List<TeamScoreUiModel>? = null,
    val lastUpdate: String? = null,
)

data class TeamScoreUiModel(
    val name: String,
    val score: String,
)