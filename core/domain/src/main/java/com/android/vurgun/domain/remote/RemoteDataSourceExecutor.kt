package com.android.vurgun.domain.remote

import com.android.vurgun.di.AppDispatchers
import com.android.vurgun.di.Dispatcher
import com.android.vurgun.domain.exception.DomainErrorException
import com.android.vurgun.network.data.NetworkResponse
import com.android.vurgun.network.data.executeWithRetry
import com.android.vurgun.network.exception.NetworkFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSourceExecutor @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    @Throws(DomainErrorException::class)
    suspend fun <S, E> execute(
        apiCall: suspend () -> NetworkResponse<S, E>,
    ): S {
        return withContext(ioDispatcher) {
            when (
                val networkResponse = executeWithRetry {
                    apiCall.invoke()
                }
            ) {
                is NetworkResponse.Success -> {
                    networkResponse.body
                }

                is NetworkResponse.Error -> {
                    when (networkResponse) {
                        is NetworkResponse.NetworkError -> {
                            when (val networkError = networkResponse.error) {
                                is NetworkFailure.TimeOutError -> {
                                    throw DomainErrorException(rootException = Exception("Time out error"))
                                }

                                is NetworkFailure.NoConnectivityError -> {
                                    throw DomainErrorException(rootException = Exception("No connectivity error"))
                                }

                                is NetworkFailure.SecureConnectionError -> {
                                    throw DomainErrorException(rootException = Exception("Secure connection error"))
                                }

                                else -> throw DomainErrorException(networkError)
                            }
                        }

                        is NetworkResponse.ServerError -> {
                            throw DomainErrorException(rootException = Exception("Server error code :  ${networkResponse.code}"))
                        }

                        is NetworkResponse.UnknownError -> {
                            throw DomainErrorException(rootException = Exception("Unknown error"))
                        }
                    }
                }
            }
        }
    }
}