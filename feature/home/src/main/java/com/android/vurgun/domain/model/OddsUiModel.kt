package com.android.vurgun.domain.model

data class OddsUiModel(
    val id: String,
    val sportKey: String,
    val sportTitle: String,
    val commenceTime: String,
    val homeTeam: String,
    val awayTeam: String,
    val bookmakers: List<BookmakerUiModel>
)

data class BookmakerUiModel(
    val key: String,
    val title: String,
    val lastUpdate: String,
    val markets: List<MarketUiModel>
)

data class MarketUiModel(
    val key: String,
    val lastUpdate: String,
    val outcomes: List<OutcomeUiModel>
)

data class OutcomeUiModel(
    val name: String,
    val price: Double,
    val point: Double? = null
)