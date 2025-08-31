package com.android.vurgun.domain.usecase

import com.android.vurgun.di.AppDispatchers
import com.android.vurgun.di.Dispatcher
import com.android.vurgun.domain.mapper.toUiModel
import com.android.vurgun.domain.model.SportUiModel
import com.android.vurgun.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetSportsUseCase @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val repository: HomeRepository,
) : DefaultUseCase<GetSportsUseCase.Params, List<SportUiModel>>(ioDispatcher) {

    override suspend fun run(input: Params): List<SportUiModel> {
        return repository.getSports(all = input.all).toUiModel()
    }

    data class Params(
        val all: Boolean = false,
    )
}