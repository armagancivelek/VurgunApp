package com.android.vurgun.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.android.vurgun.data.encryption.EncryptionHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreHelperImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val encryptionHelper: EncryptionHelper,
) : DataStoreHelper {

    override fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T?): Flow<T?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
    }

    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun <T> removePreference(key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    override suspend fun removePreferences(keys: List<Preferences.Key<out Any>>) {
        dataStore.edit { preferences ->
            keys.forEach { key ->
                preferences.remove(key)
            }
        }
    }

    override suspend fun clearAllPreference() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override fun getEncryptedBoolean(key: Preferences.Key<String>, defaultValue: Boolean): Flow<Boolean> {
        return getPreference(key, null)
            .map { encryptedValue ->
                if (encryptedValue != null) {
                    try {
                        val decryptedValue = encryptionHelper.decryptData(encryptedValue) as String
                        decryptedValue.toBoolean()
                    } catch (e: Exception) {
                        defaultValue
                    }
                } else {
                    defaultValue
                }
            }
    }

    override suspend fun putEncryptedBoolean(key: Preferences.Key<String>, value: Boolean) {
        val encryptedValue = encryptionHelper.encryptData(value.toString())
        putPreference(key, encryptedValue)
    }
}