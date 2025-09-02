package com.android.vurgun.home_api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScoresResponse(
    @SerialName("id")
    val id: String,
    @SerialName("sport_key")
    val sportKey: String,
    @SerialName("sport_title")
    val sportTitle: String,
    @SerialName("commence_time")
    val commenceTime: String,
    @SerialName("completed")
    val completed: Boolean,
    @SerialName("home_team")
    val homeTeam: String,
    @SerialName("away_team")
    val awayTeam: String,
    @SerialName("scores")
    val scores: List<TeamScore>? = null,
    @SerialName("last_update")
    val lastUpdate: String? = null,
)

@Serializable
data class TeamScore(
    @SerialName("name")
    val name: String,
    @SerialName("score")
    val score: String,
)