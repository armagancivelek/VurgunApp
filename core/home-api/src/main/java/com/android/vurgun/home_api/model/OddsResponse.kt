package com.android.vurgun.home_api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OddsResponse(
    @SerialName("id")
    val id: String,
    @SerialName("sport_key")
    val sportKey: String,
    @SerialName("sport_title")
    val sportTitle: String,
    @SerialName("commence_time")
    val commenceTime: String,
    @SerialName("home_team")
    val homeTeam: String,
    @SerialName("away_team")
    val awayTeam: String,
    @SerialName("bookmakers")
    val bookmakers: List<Bookmaker>,
)

@Serializable
data class Bookmaker(
    @SerialName("key")
    val key: String,
    @SerialName("title")
    val title: String,
    @SerialName("last_update")
    val lastUpdate: String,
    @SerialName("markets")
    val markets: List<Market>,
)

@Serializable
data class Market(
    @SerialName("key")
    val key: String,
    @SerialName("last_update")
    val lastUpdate: String,
    @SerialName("outcomes")
    val outcomes: List<Outcome>,
)

@Serializable
data class Outcome(
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: Double,
    @SerialName("point")
    val point: Double? = null,
)