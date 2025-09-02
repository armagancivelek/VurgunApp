package com.android.vurgun.di

import com.android.vurgun.common.stringprovider.StringProvider
import com.android.vurgun.common.stringprovider.StringProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StringProviderModule {

    @Binds
    abstract fun bindStringProvider(
        stringProviderImpl: StringProviderImpl
    ): StringProvider
}
