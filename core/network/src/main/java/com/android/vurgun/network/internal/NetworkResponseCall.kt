package com.android.vurgun.network.internal
import com.android.vurgun.network.data.NetworkResponse
import com.android.vurgun.network.data.asNetworkResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.lang.reflect.Type

/**
 * A custom [Call] that wraps a regular Retrofit call and adapts the
 * response to a [NetworkResponse]
 */
internal class NetworkResponseCall<S, E>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>,
    private val successBodyType: Type,
) : Call<NetworkResponse<S, E>> {

    override fun enqueue(callback: Callback<NetworkResponse<S, E>>) = synchronized(this) {
        delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val networkResponse = response.asNetworkResponse(successBodyType, errorConverter)
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = throwable.asNetworkResponse<S, E>(successBodyType, errorConverter)
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted(): Boolean = synchronized(this) {
        delegate.isExecuted
    }

    override fun clone(): Call<NetworkResponse<S, E>> = NetworkResponseCall(
        delegate.clone(),
        errorConverter,
        successBodyType,
    )

    override fun isCanceled(): Boolean = synchronized(this) {
        delegate.isCanceled
    }

    override fun cancel() = synchronized(this) {
        delegate.cancel()
    }

    override fun execute(): Response<NetworkResponse<S, E>> {
        val retrofitResponse = delegate.execute()
        val networkResponse = retrofitResponse.asNetworkResponse(successBodyType, errorConverter)
        return Response.success(networkResponse)
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}