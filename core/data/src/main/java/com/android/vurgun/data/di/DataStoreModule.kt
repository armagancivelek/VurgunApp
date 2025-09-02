package com.android.vurgun.data.di

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import com.android.vurgun.data.data.DataStoreRepository
import com.android.vurgun.data.data.DataStoreRepositoryImpl
import com.android.vurgun.data.encryption.EncryptionHelper
import com.android.vurgun.data.encryption.EncryptionHelperImpl
import com.android.vurgun.data.preference.DataStoreHelper
import com.android.vurgun.data.preference.DataStoreHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private val Context.dataStore by preferencesDataStore(
        name = DataStoreRepositoryImpl.PREFS_NAME,
        produceMigrations = { context ->
            listOf(
                SharedPreferencesMigration(
                    context,
                    DataStoreRepositoryImpl.PREFS_NAME,
                ),
            )
        },
    )

    @Singleton
    @Provides
    fun provideEncryptionHelper(): EncryptionHelper {
        return EncryptionHelperImpl()
    }

    @Singleton
    @Provides
    fun provideDataStoreHelper(
        @ApplicationContext context: Context,
        encryptionHelper: EncryptionHelper,
    ): DataStoreHelper {
        return DataStoreHelperImpl(context.dataStore, encryptionHelper)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        dataStoreHelper: DataStoreHelper,
    ): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStoreHelper)
    }
}