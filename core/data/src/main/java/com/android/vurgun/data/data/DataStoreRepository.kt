package com.android.vurgun.data.data

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveOnBoardingState(value: Boolean)

    fun readOnBoardingState(): Flow<Boolean>
}
