package com.android.vurgun.domain.mapper

import com.android.vurgun.domain.model.BookmakerUiModel
import com.android.vurgun.domain.model.MarketUiModel
import com.android.vurgun.domain.model.OddsUiModel
import com.android.vurgun.domain.model.OutcomeUiModel
import com.android.vurgun.domain.model.ScoresUiModel
import com.android.vurgun.domain.model.SportUiModel
import com.android.vurgun.domain.model.TeamScoreUiModel
import com.android.vurgun.home_api.model.Bookmaker
import com.android.vurgun.home_api.model.Market
import com.android.vurgun.home_api.model.OddsResponse
import com.android.vurgun.home_api.model.Outcome
import com.android.vurgun.home_api.model.ScoresResponse
import com.android.vurgun.home_api.model.Sport
import com.android.vurgun.home_api.model.TeamScore

fun Sport.toUiModel(): SportUiModel {
    return SportUiModel(
        key = key,
        group = group,
        title = title,
        description = description,
        active = active,
        hasOutrights = hasOutrights
    )
}

fun List<Sport>.toUiModel(): List<SportUiModel> {
    return map { it.toUiModel() }
}

fun OddsResponse.toUiModel(): OddsUiModel {
    return OddsUiModel(
        id = id,
        sportKey = sportKey,
        sportTitle = sportTitle,
        commenceTime = commenceTime,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        bookmakers = bookmakers.map { it.toUiModel() }
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
        markets = markets.map { it.toUiModel() }
    )
}

fun Market.toUiModel(): MarketUiModel {
    return MarketUiModel(
        key = key,
        lastUpdate = lastUpdate,
        outcomes = outcomes.map { it.toUiModel() }
    )
}

fun Outcome.toUiModel(): OutcomeUiModel {
    return OutcomeUiModel(
        name = name,
        price = price,
        point = point
    )
}

fun ScoresResponse.toUiModel(): ScoresUiModel {
    return ScoresUiModel(
        id = id,
        sportKey = sportKey,
        sportTitle = sportTitle,
        commenceTime = commenceTime,
        completed = completed,
        homeTeam = homeTeam,
        awayTeam = awayTeam,
        scores = scores?.map { it.toUiModel() },
        lastUpdate = lastUpdate
    )
}

fun List<ScoresResponse>.toScoresUiModel(): List<ScoresUiModel> {
    return map { it.toUiModel() }
}

fun TeamScore.toUiModel(): TeamScoreUiModel {
    return TeamScoreUiModel(
        name = name,
        score = score
    )
}