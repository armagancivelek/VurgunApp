package com.android.vurgun.domain.usecase

import com.android.vurgun.di.AppDispatchers
import com.android.vurgun.di.Dispatcher
import com.android.vurgun.domain.mapper.toScoresUiModel
import com.android.vurgun.domain.model.ScoresUiModel
import com.android.vurgun.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetScoresUseCase @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repository: HomeRepository,
) : DefaultUseCase<GetScoresUseCase.Params, List<ScoresUiModel>>(ioDispatcher) {

    override suspend fun run(input: Params): List<ScoresUiModel> {
        return repository.getScores(
            sport = input.sport,
            daysFrom = input.daysFrom,
            dateFormat = input.dateFormat,
        ).toScoresUiModel()
    }

    data class Params(
        val sport: String,
        val daysFrom: Int = 3,
        val dateFormat: String = "iso",
    )
}