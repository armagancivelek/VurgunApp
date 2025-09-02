package com.android.vurgun.domain.repository

import com.android.vurgun.home_api.model.OddsResponse
import com.android.vurgun.home_api.model.ScoresResponse
import com.android.vurgun.home_api.model.Sport

interface HomeRepository {
    suspend fun getSports(all: Boolean = false): List<Sport>
    suspend fun getOdds(
        sport: String,
        regions: String = "us,uk,eu",
        markets: String = "h2h",
        oddsFormat: String = "decimal",
        dateFormat: String = "iso",
    ): List<OddsResponse>
    suspend fun getScores(
        sport: String,
        daysFrom: Int = 3,
        dateFormat: String = "iso",
    ): List<ScoresResponse>
}