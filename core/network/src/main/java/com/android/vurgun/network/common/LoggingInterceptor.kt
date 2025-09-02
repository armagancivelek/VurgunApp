package com.android.vurgun.network.common

import com.michaelflisar.lumberjack.core.L
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.newBuilder()
        request = builder.build()
        val response = chain.proceed(request)
        try {
            L.tag(LOGGING_URL).i { "${request.url} -- StatusCode ${response.code}" }
        } catch (e: Exception) {
            // Fallback to system logging if Lumberjack is not initialized
            println("$LOGGING_URL: ${request.url} -- StatusCode ${response.code}")
        }
        return response
    }

    companion object {
        const val LOGGING_URL = "Logging-URL"
    }
}