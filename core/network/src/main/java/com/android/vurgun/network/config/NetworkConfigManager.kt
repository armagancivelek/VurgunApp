package com.android.vurgun.network.config

import javax.inject.Inject

interface NetworkConfigManager {

    fun getDomainBaseUrl(): String
}
class NetworkConfigManagerImpl @Inject constructor(
    private val configType: NetworkConfigType,
) : NetworkConfigManager {
    override fun getDomainBaseUrl(): String {
        return when (configType) {
            NetworkConfigType.STAGING -> {
                STAGING_BASE_URL
            }

            NetworkConfigType.PRODUCTION -> {
                PRODUCTION_BASE_URL
            }
        }
    }
    companion object {
        private const val STAGING_BASE_URL = "https://api.the-odds-api.com"
        private const val PRODUCTION_BASE_URL = "https://api.the-odds-api.com"
    }
}
