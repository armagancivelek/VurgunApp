package com.android.vurgun.home_api

import com.android.vurgun.home_api.model.OddsResponse
import com.android.vurgun.home_api.model.ScoresResponse
import com.android.vurgun.home_api.model.Sport
import com.android.vurgun.network.data.NetworkErrorResponse
import com.android.vurgun.network.data.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApiService {

    @GET("v4/sports")
    suspend fun getSports(
        @Query("all") all: Boolean = false,
    ): NetworkResponse<List<Sport>, NetworkErrorResponse>

    @GET("v4/sports/{sport}/odds")
    suspend fun getOdds(
        @Path("sport") sport: String,
        @Query("regions") regions: String = "us,uk,eu",
        @Query("markets") markets: String = "h2h",
        @Query("oddsFormat") oddsFormat: String = "decimal",
        @Query("dateFormat") dateFormat: String = "iso",
    ): NetworkResponse<List<OddsResponse>, NetworkErrorResponse>

    @GET("v4/sports/{sport}/scores")
    suspend fun getScores(
        @Path("sport") sport: String,
        @Query("daysFrom") daysFrom: Int = 3,
        @Query("dateFormat") dateFormat: String = "iso",
    ): NetworkResponse<List<ScoresResponse>, NetworkErrorResponse>

    @GET("v4/sports/{sport}/events")
    suspend fun getEvents(
        @Path("sport") sport: String,
        @Query("dateFormat") dateFormat: String = "iso",
    ): NetworkResponse<List<OddsResponse>, NetworkErrorResponse>
}