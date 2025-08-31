package com.android.vurgun.network.common

import com.android.vurgun.di.BuildConfigProvider
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject
constructor(
    val provider: BuildConfigProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.newBuilder()

        val accessToken = provider.getApiKey()

        accessToken.let {
            builder.addHeaders(it)
        }

        request = builder.build()
        return chain.proceed(request)
    }

    private fun Request.Builder.addHeaders(accessToken: String) =
        this.apply {
            header("Content-Type", "application/json;charset=utf-8")
            header("Accept-Language", Locale.getDefault().language)
            header("device-type", "android")

            if (accessToken.isNotEmpty()) header(HEADER_AUTHORIZATION, "$CLIENT_ID $accessToken")
        }

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val CLIENT_ID = "Client-ID"
    }
}
