package com.android.vurgun.di

private const val DEV_FLAVOR = "dev"

interface BuildConfigProvider {
    fun isDevApp(): Boolean
    fun getVersionName(): String
    fun getApiKey(): String
}

