package com.android.vurgun.domain.mapper

import com.android.vurgun.domain.model.ScoresUiModel
import com.android.vurgun.domain.model.TeamScoreUiModel
import com.android.vurgun.home_api.model.ScoresResponse
import com.android.vurgun.home_api.model.TeamScore

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
        lastUpdate = lastUpdate,
    )
}

fun List<ScoresResponse>.toScoresUiModel(): List<ScoresUiModel> {
    return map { it.toUiModel() }
}

fun TeamScore.toUiModel(): TeamScoreUiModel {
    return TeamScoreUiModel(
        name = name,
        score = score,
    )
}