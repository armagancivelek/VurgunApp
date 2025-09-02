package com.android.vurgun.network.common

import com.android.vurgun.di.BuildConfigProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject
constructor(
    val provider: BuildConfigProvider,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val apiKey = provider.getApiKey()

        if (apiKey.isNotEmpty()) {
            val url = request.url.newBuilder()
                .addQueryParameter("apiKey", apiKey)
                .build()

            request = request.newBuilder()
                .url(url)
                .build()
        }

        return chain.proceed(request)
    }
}