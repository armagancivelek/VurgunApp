package com.android.vurgun.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BuildConfigEntryPoint {
    fun buildConfigProvider(): BuildConfigProvider
}