package com.android.vurgun.di

import com.android.vurgun.data.repository.HomeRepositoryImpl
import com.android.vurgun.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HomeRepositoryModule {

    @Binds
    fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository

}
