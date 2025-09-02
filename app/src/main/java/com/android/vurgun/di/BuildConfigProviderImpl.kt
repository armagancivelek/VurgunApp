package com.android.vurgun.di

import com.android.vurgun.BuildConfig
import javax.inject.Inject

class BuildConfigProviderImpl @Inject constructor() : BuildConfigProvider {
    override fun isDevApp(): Boolean = BuildConfig.FLAVOR == "dev"
    override fun getVersionName(): String = BuildConfig.VERSION_NAME
    override fun getApiKey(): String = BuildConfig.API_KEY
}