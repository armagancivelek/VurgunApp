package com.android.vurgun.network.data

import com.android.vurgun.network.exception.NetworkFailure
import retrofit2.HttpException
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLPeerUnverifiedException

/**
 * Represents the result of a network request made using Retrofit. It can be either in a success
 * state or an error state, depending on the result of the request.
 *
 * [S] represents the deserialized body of a successful response.
 * [E] represents the deserialized body of an unsuccessful response.
 *
 * A network request is considered to be successful if it received a 2xx response code, and unsuccessful
 * otherwise.
 *
 * If the network request was successful and Retrofit successfully deserialized the body, the [NetworkResponse]
 * is [NetworkResponse.Success]. If you do not expect a successful response to contain a body, you must specify
 * [S] as [Unit] or use [CompletableResponse].
 *
 * If the network request failed due to:
 * - Non-2xx response from the server, the [NetworkResponse] is [NetworkResponse.ServerError] containing the
 * deserialized body of the response ([E])
 * - Internet connectivity problems, the [NetworkResponse] is [NetworkResponse.NetworkError]
 * - Any other problems (e.g. Serialization errors), the [NetworkResponse] is [NetworkResponse.UnknownError].
 */
@Serializable
public sealed interface NetworkResponse<S, E> {
    /**
     * The result of a successful network request.
     *
     * If you expect your server response to not contain a body, set the success body type ([S]) to [Unit].
     * If you expect your server response to sometimes not contain a body (e.g. for response code 204), set
     * [S] to [Unit] and deserialize the raw [response] manually when needed.
     *
     * @param body The parsed body of the successful response.
     * @param response The original [Response] from Retrofit
     */
    public data class Success<S, E>(
        public val body: S,
        public val response: Response<*>,
    ) : NetworkResponse<S, E> {
        /**
         * The status code returned by the server.
         *
         * Alias for [Response.code] of the original response
         */
        public val code: Int
            get() = response.code()

        /**
         * The headers returned by the server.
         *
         * Alias for [Response.headers] of the original response
         */
        public val headers: Headers
            get() = response.headers()
    }

    /**
     * The result of a failed network request.
     *
     * A failed network request can either be due to a non-2xx response code and contain an error
     * body ([ServerError]), or due to a connectivity error ([NetworkError]), or due to an unknown
     * error ([UnknownError]).
     */
    public sealed interface Error<S, E> : NetworkResponse<S, E> {
        /**
         * The body of the failed network request, if available.
         */
        public val body: E?

        /**
         * The underlying error of the failed network request, if available.
         */
        public val error: Throwable?
    }

    /**
     * The result of a non 2xx response to a network request.
     *
     * This result may or may not contain a [body], depending on the body
     * supplied by the server.
     */
    public data class ServerError<S, E>(
        public override val body: E?,
        public val response: Response<*>?,
    ) : Error<S, E> {
        /**
         * The status code returned by the server.
         *
         * Alias for [Response.code] of the original response
         */
        public val code: Int? = response?.code()

        /**
         * The headers returned by the server.
         *
         * Alias for [Response.headers] of the original response
         */
        public val headers: Headers? = response?.headers()

        /**
         * Always `null` for a [ServerError].
         */
        override val error: Throwable? = null
    }

    /**
     * The result of a network connectivity error
     */
    public data class NetworkError<S, E>(
        public override val error: Exception,
    ) : Error<S, E> {

        /**
         * Always `null` for a [NetworkError]
         */
        override val body: E? = null
    }

    /**
     * Result of an unknown error during a network request
     * (e.g. Serialization errors)
     */
    public data class UnknownError<S, E>(
        public override val error: Throwable,
        public val response: Response<*>?,
    ) : Error<S, E> {
        /**
         * Always `null` for an [UnknownError]
         */
        override val body: E? = null

        /**
         * The status code returned by the server.
         *
         * Alias for [Response.code] of the original response
         */
        public val code: Int? = response?.code()

        /**
         * The headers returned by the server.
         *
         * Alias for [Response.headers] of the original response
         */
        public val headers: Headers? = response?.headers()
    }
}

/**
 * An alias for a [NetworkResponse] with no expected response body ([Unit]).
 *
 * Useful for specifying return types of API calls that do not return a useful value.
 */
public typealias CompletableResponse<E> = NetworkResponse<Unit, E>

/**
 * Maps a [Response] to a [NetworkResponse].
 *
 *
 * @param errorConverter Retrofit provided body converter to parse the error body of the response
 * @return A subtype of [NetworkResponse] based on the response of the network request
 */
internal fun <S, E> Response<S>.asNetworkResponse(
    successType: Type,
    errorConverter: Converter<ResponseBody, E>,
): NetworkResponse<S, E> {
    return if (!isSuccessful) {
        parseUnsuccessfulResponse(this, errorConverter)
    } else {
        parseSuccessfulResponse(this, successType)
    }
}

/**
 * Maps an unsuccessful [Response] to [NetworkResponse.Error].
 *
 * Control flow:
 * 1 Try to parse the error body using [errorConverter].
 * 2. If error body is parsed successfully, return it as [NetworkResponse.ServerError]
 * 3 Otherwise, assume we ran into an unknown error (probably related to serialization)
 * and return [NetworkResponse.UnknownError]
 *
 * @param response Unsuccessful response
 * @param errorConverter Retrofit [Converter] to parse the error body
 * @return A subtype of [NetworkResponse.Error]
 */
private fun <S, E> parseUnsuccessfulResponse(
    response: Response<S>,
    errorConverter: Converter<ResponseBody, E>,
): NetworkResponse.Error<S, E> {
    if (NetworkFailure.NoConnectivityError.ERROR_CODE == response.code() &&
        NetworkFailure.NoConnectivityError.ERROR_MESSAGE == response.message()
    ) {
        return NetworkResponse.NetworkError(NetworkFailure.NoConnectivityError)
    }
    val errorBody: ResponseBody = response.errorBody() ?: return NetworkResponse.ServerError(null, response)

    return try {
        val convertedBody = errorConverter.convert(errorBody)
        NetworkResponse.ServerError(convertedBody, response)
    } catch (error: Throwable) {
        NetworkResponse.UnknownError(error, response)
    }
}

/**
 * Maps a successful [Response] to [NetworkResponse]
 *
 * Control flow:
 *
 * - If [response] body is null:
 *      - If [successType] is [Unit], return [NetworkResponse.Success] with [Unit] as the body
 *      - Else return a [NetworkResponse.ServerError] with a null body
 * - If response body is not null, return [NetworkResponse.Success] with the parsed body
 */
private fun <S, E> parseSuccessfulResponse(response: Response<S>, successType: Type): NetworkResponse<S, E> {
    val responseBody: S? = response.body()
    if (responseBody == null) {
        if (successType === Unit::class.java) {
            @Suppress("UNCHECKED_CAST")
            return NetworkResponse.Success<Unit, E>(Unit, response) as NetworkResponse<S, E>
        }

        return NetworkResponse.ServerError(null, response)
    }

    return NetworkResponse.Success(responseBody, response)
}

/**
 * Maps a [Throwable] to a [NetworkResponse].
 *
 * - If the error is [IOException], return [NetworkResponse.NetworkError].
 * - If the error is [HttpException], attempt to parse the underlying response and return the result
 * - Else return [NetworkResponse.UnknownError] that wraps the original error
 */
internal fun <S, E> Throwable.asNetworkResponse(
    successType: Type,
    errorConverter: Converter<ResponseBody, E>,
): NetworkResponse<S, E> {
    return when (this) {
        is SocketTimeoutException -> NetworkResponse.NetworkError(NetworkFailure.TimeOutError(this))
        is UnknownHostException -> NetworkResponse.NetworkError(NetworkFailure.NoConnectivityError)
        is SSLPeerUnverifiedException -> NetworkResponse.NetworkError(NetworkFailure.SecureConnectionError)
        is IOException -> NetworkResponse.NetworkError(this)
        is HttpException -> {
            val response = response()
            if (response == null) {
                NetworkResponse.ServerError(null, null)
            } else {
                @Suppress("UNCHECKED_CAST")
                response.asNetworkResponse(successType, errorConverter) as NetworkResponse<S, E>
            }
        }
        else -> NetworkResponse.UnknownError(this, null)
    }
}

/**
 * Overloaded invoke operator to get the successful body or null in NetworkResponse class
 *
 * @param S the success body type of [NetworkResponse]
 * @param E the error body type of [NetworkResponse]
 *
 * Example:
 * val usersResponse = executeWithRetry { getUsers() }
 *
 * println(usersResponse() ?: "No users found")
 */
public operator fun <S : Any, E : Any> NetworkResponse<S, E>.invoke(): S? {
    return if (this is NetworkResponse.Success) body else null
}

/**
 * Retries the given [block] for the specified number of times in the case of [NetworkFailure.NoConnectivityError]
 *
 * @param S The success body type of [NetworkResponse]
 * @param E The error body type of [NetworkResponse]
 * @param times The number of times this request should be retried
 * @param initialDelay The initial amount of time to wait before retrying
 * @param maxDelay The max amount of time to wait before retrying
 * @param factor Multiply current delay time with this on each retry
 * @param block The suspending function to be retried
 * @return The NetworkResponse value whether it be successful or failed after retrying
 */
public suspend fun <S, E> executeWithRetry(
    times: Int = 4,
    initialDelay: Long = 1000,
    maxDelay: Long = 2000,
    factor: Double = 2.0,
    block: suspend () -> NetworkResponse<S, E>,
): NetworkResponse<S, E> {
    var currentDelay = initialDelay
    repeat(times - 1) {
        when (val response = block()) {
            is NetworkResponse.NetworkError -> {
                if (response.error == NetworkFailure.NoConnectivityError) {
                    delay(currentDelay)
                    currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
                } else {
                    return response
                }
            }
            else -> return response
        }
    }
    return block() // last attempt
}