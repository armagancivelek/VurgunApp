package com.android.vurgun.domain.repository

import com.android.vurgun.response.OddsResponse
import com.android.vurgun.request.Sport

interface HomeRepository {
    suspend fun getSports(all: Boolean = false): List<Sport>
    suspend fun getOdds(
        sport: String,
        regions: String,
        markets: String,
        oddsFormat: String,
        dateFormat: String
    ): List<OddsResponse>
}