package com.android.vurgun

import android.app.Application
import com.android.vurgun.di.BuildConfigEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val entryPoint = EntryPointAccessors.fromApplication(
            this,
            BuildConfigEntryPoint::class.java,
        )
    }
}