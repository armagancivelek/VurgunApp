package com.android.vurgun.domain.usecase

import com.android.vurgun.di.AppDispatchers
import com.android.vurgun.di.Dispatcher
import com.android.vurgun.domain.mapper.toOddsUiModel
import com.android.vurgun.domain.model.OddsUiModel
import com.android.vurgun.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetOddsUseCase @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repository: HomeRepository,
) : DefaultUseCase<GetOddsUseCase.Params, List<OddsUiModel>>(ioDispatcher) {

    override suspend fun run(input: Params): List<OddsUiModel> {
        return repository.getOdds(
            sport = input.sport,
            regions = input.regions,
            markets = input.markets,
            oddsFormat = input.oddsFormat,
            dateFormat = input.dateFormat,
        ).toOddsUiModel()
    }

    data class Params(
        val sport: String,
        val regions: String = "us,uk,eu",
        val markets: String = "h2h",
        val oddsFormat: String = "decimal",
        val dateFormat: String = "iso",
    )
}