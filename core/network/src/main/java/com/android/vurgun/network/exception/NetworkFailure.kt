package com.android.vurgun.network.exception

import com.android.vurgun.network.data.NetworkErrorResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

sealed class NetworkFailure : Exception() {
    data class TimeOutError(val exception: SocketTimeoutException) : NetworkFailure() {
        val errorCode: Int = 12002
        private val errorMessageFormatted = "Connection has timed out: [${exception.message}]"
        override val cause: Throwable = HttpException(
            Response.error<NetworkErrorResponse>(
                errorCode,
                errorMessageFormatted.toResponseBody(null),
            ),
        )
    }
    data object NoConnectivityError : NetworkFailure() {
        const val ERROR_CODE: Int = 12163
        const val ERROR_MESSAGE = "No Internet Connection"
        override val cause: Throwable = HttpException(
            Response.error<NetworkErrorResponse>(
                ERROR_CODE,
                ERROR_MESSAGE.toResponseBody(null),
            ),
        )
    }

    data object SecureConnectionError : NetworkFailure() {
        private const val ERROR_CODE: Int = 495
        private const val ERROR_MESSAGE = "SSL Certificate Error"
        override val cause: Throwable = HttpException(
            Response.error<NetworkErrorResponse>(
                ERROR_CODE,
                ERROR_MESSAGE.toResponseBody(null),
            ),
        )
    }

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : NetworkFailure()
}