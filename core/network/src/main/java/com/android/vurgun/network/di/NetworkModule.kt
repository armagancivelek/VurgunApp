package com.android.vurgun.network.di

import android.content.Context
import com.android.vurgun.di.BuildConfigProvider
import com.android.vurgun.network.common.AuthInterceptor
import com.android.vurgun.network.common.InternalClient
import com.android.vurgun.network.common.LoggingInterceptor
import com.android.vurgun.network.common.NetworkConnectivityManager
import com.android.vurgun.network.common.NetworkConnectivityManagerImpl
import com.android.vurgun.network.common.NetworkResponseAdapterFactory
import com.android.vurgun.network.common.PublicClient
import com.android.vurgun.network.config.NetworkConfigManager
import com.android.vurgun.network.config.NetworkConfigManagerImpl
import com.android.vurgun.network.config.NetworkConfigType
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val CLIENT_TIME_OUT_SEC = 15L

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideJson(): Json {
        val json = Json {
            allowTrailingComma = true
            ignoreUnknownKeys = true
        }
        return json
    }

    @Provides
    @Singleton
    fun providerNetworkConfigManager(): NetworkConfigManager {
        return NetworkConfigManagerImpl(
            configType = NetworkConfigType.STAGING,
        )
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityManager(@ApplicationContext context: Context): NetworkConnectivityManager {
        return NetworkConnectivityManagerImpl(context)
    }


    @Provides
    @Singleton
    fun providerAuthInterceptor(provider: BuildConfigProvider): AuthInterceptor {
        return AuthInterceptor(
            provider = provider,
        )
    }

    @Provides
    @Singleton
    @InternalClient
    fun provideInternalDebugOkHttpClient(
        authInterceptor: AuthInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        customLoggingInterceptor: LoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CLIENT_TIME_OUT_SEC, TimeUnit.SECONDS)
            .readTimeout(CLIENT_TIME_OUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(customLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @InternalClient
    fun provideInternalRetrofit(
        @InternalClient okHttpClient: OkHttpClient,
        networkConfigManager: NetworkConfigManager,
        json: Json,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(networkConfigManager.getDomainBaseUrl())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType(),
                ),
            )
            .callFactory { okHttpClient.newCall(it) }
            .build()
    }

    @Provides
    @Singleton
    internal fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).collector(ChuckerCollector(context)).build()
    }


    @Provides
    @Singleton
    @PublicClient
    fun providePublicDebugOkHttpClient(
        customLoggingInterceptor: LoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CLIENT_TIME_OUT_SEC, TimeUnit.SECONDS)
            .readTimeout(CLIENT_TIME_OUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(customLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @PublicClient
    fun providePublicRetrofit(
        @PublicClient client: OkHttpClient,
        networkConfigManager: NetworkConfigManager,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(networkConfigManager.getDomainBaseUrl())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType(),
                ),
            )
            .callFactory { client.newCall(it) }
            .build()
    }


}
