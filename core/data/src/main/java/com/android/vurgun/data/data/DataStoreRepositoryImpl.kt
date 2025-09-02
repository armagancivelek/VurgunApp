package com.android.vurgun.data.data

import com.android.vurgun.data.preference.DataStoreHelper

class DataStoreRepositoryImpl(
    private val dataStoreHelper: DataStoreHelper,
) : DataStoreRepository {

    companion object {
        const val PREFS_NAME = "vurgun-app-android"
    }

    private object PreferencesKey {}

}