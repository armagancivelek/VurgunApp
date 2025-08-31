package com.android.vurgun.home_api

import com.android.vurgun.network.common.InternalClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeApiServiceModule {

    @Provides
    @Singleton
    fun provideHomeApiService(@InternalClient retrofit: Retrofit): HomeApiService {
        return retrofit.create(HomeApiService::class.java)
    }
}
