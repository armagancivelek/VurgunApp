package com.android.vurgun.domain.usecase

import com.android.vurgun.di.AppDispatchers
import com.android.vurgun.di.Dispatcher
import com.android.vurgun.domain.mapper.toGroupedUiModel
import com.android.vurgun.domain.model.SportGroupUiModel
import com.android.vurgun.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetSportsUseCase @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repository: HomeRepository,
) : DefaultUseCase<GetSportsUseCase.Params, List<SportGroupUiModel>>(ioDispatcher) {

    override suspend fun run(input: Params): List<SportGroupUiModel> {
        return repository.getSports(all = input.all).toGroupedUiModel()
    }

    data class Params(
        val all: Boolean = false,
    )
}