package com.android.vurgun.di

import com.android.vurgun.analytics.FirebaseEventTracker
import com.android.vurgun.analytics.FirebaseEventTrackerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModule {

    @Binds
    abstract fun bindFirebaseEventTracker(
        firebaseEventTracker: FirebaseEventTrackerImpl
    ): FirebaseEventTracker


}
