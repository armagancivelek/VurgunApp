package com.android.vurgun.data.data

import androidx.datastore.preferences.core.stringPreferencesKey
import com.android.vurgun.data.preference.DataStoreHelper
import kotlinx.coroutines.flow.Flow


class DataStoreRepositoryImpl(
    private val dataStoreHelper: DataStoreHelper
) : DataStoreRepository {

    companion object {
        const val PREFS_NAME = "vurgun-app-android"
    }


    private object PreferencesKey {
        val ON_BOARDING_KEY = stringPreferencesKey(name = "on_boarding_completed")
    }

    override suspend fun saveOnBoardingState(value: Boolean) {
        dataStoreHelper.putEncryptedBoolean(PreferencesKey.ON_BOARDING_KEY, value)
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStoreHelper.getEncryptedBoolean(PreferencesKey.ON_BOARDING_KEY, false)
    }
}
