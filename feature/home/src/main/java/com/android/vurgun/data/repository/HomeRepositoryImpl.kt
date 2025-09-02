package com.android.vurgun.data.repository

import com.android.vurgun.domain.remote.RemoteDataSourceExecutor
import com.android.vurgun.domain.repository.HomeRepository
import com.android.vurgun.home_api.HomeApiService
import com.android.vurgun.response.OddsResponse
import com.android.vurgun.request.Sport
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApiService: HomeApiService,
    private val remoteDataSourceExecutor: RemoteDataSourceExecutor,
) : HomeRepository {
    override suspend fun getSports(all: Boolean): List<Sport> {
        return remoteDataSourceExecutor.execute {
            homeApiService.getSports(all = all)
        }
    }

    override suspend fun getOdds(
        sport: String,
        regions: String,
        markets: String,
        oddsFormat: String,
        dateFormat: String,
    ): List<OddsResponse> {
        return remoteDataSourceExecutor.execute {
            homeApiService.getOdds(
                sport = sport,
                regions = regions,
                markets = markets,
                oddsFormat = oddsFormat,
                dateFormat = dateFormat,
            )
        }
    }

}