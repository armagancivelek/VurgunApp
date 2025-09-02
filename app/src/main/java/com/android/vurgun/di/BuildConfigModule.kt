package com.android.vurgun.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BuildConfigModule {

    @Provides
    @Singleton
    fun provideBuildConfigProvider(
        impl: BuildConfigProviderImpl,
    ): BuildConfigProvider = impl
}