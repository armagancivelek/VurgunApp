package com.android.vurgun.data.preference

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreHelper {

    fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T?): Flow<T?>
    suspend fun <T> putPreference(key: Preferences.Key<T>, value: T)
    suspend fun <T> removePreference(key: Preferences.Key<T>)
    suspend fun removePreferences(keys: List<Preferences.Key<out Any>>)
    suspend fun clearAllPreference()

    // Encrypted operations
    fun getEncryptedBoolean(key: Preferences.Key<String>, defaultValue: Boolean): Flow<Boolean>
    suspend fun putEncryptedBoolean(key: Preferences.Key<String>, value: Boolean)
}