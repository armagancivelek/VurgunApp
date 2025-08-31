package com.android.vurgun.domain.usecase

import com.android.vurgun.di.AppDispatchers
import com.android.vurgun.di.Dispatcher
import com.android.vurgun.domain.exception.DomainErrorException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

abstract class DefaultUseCase<INPUT, RESULT>(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(
        onStart: () -> Unit = {},
        onSuccess: (RESULT) -> Unit,
        onFailure: (DomainErrorException) -> Unit,
    ) {
        @Suppress("UNCHECKED_CAST")
        invoke(Unit as INPUT, onStart, onSuccess, onFailure)
    }

    suspend operator fun invoke(
        input: INPUT,
        onStart: () -> Unit = {},
        onSuccess: (RESULT) -> Unit,
        onFailure: (DomainErrorException) -> Unit,
    ) {
        try {
            onStart.invoke()
            val result = withContext(ioDispatcher) {
                run(input)
            }
            onSuccess(result)
        } catch (ignore: CancellationException) {
        } catch (exception: Exception) {
            onFailure(exception as? DomainErrorException ?: DomainErrorException(exception))
        }
    }

    protected abstract suspend fun run(input: INPUT): RESULT
}
