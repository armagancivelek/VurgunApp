package com.android.vurgun.domain.mapper

import com.android.vurgun.domain.model.BookmakerUiModel
import com.android.vurgun.domain.model.MarketUiModel
import com.android.vurgun.domain.model.OddsUiModel
import com.android.vurgun.domain.model.OutcomeUiModel
import com.android.vurgun.response.Bookmaker
import com.android.vurgun.response.Market
import com.android.vurgun.response.OddsResponse
import com.android.vurgun.response.Outcome

fun OddsResponse.toUiModel(): OddsUiModel {
    return OddsUiModel(
        id = id,
        sportKey = sportKey,
        sportTitle = sportTitle,
        commenceTime = commenceTime,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        bookmakers = bookmakers.map { it.toUiModel() },
    )
}

fun List<OddsResponse>.toOddsUiModel(): List<OddsUiModel> {
    return map { it.toUiModel() }
}

fun Bookmaker.toUiModel(): BookmakerUiModel {
    return BookmakerUiModel(
        key = key,
        title = title,
        lastUpdate = lastUpdate,
        markets = markets.map { it.toUiModel() },
    )
}

fun Market.toUiModel(): MarketUiModel {
    return MarketUiModel(
        key = key,
        lastUpdate = lastUpdate,
        outcomes = outcomes.map { it.toUiModel() },
    )
}

fun Outcome.toUiModel(): OutcomeUiModel {
    return OutcomeUiModel(
        name = name,
        price = price,
        point = point,
    )
}