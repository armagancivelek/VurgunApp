plugins {
    id("com.android.vurgun.application")
    id("com.android.vurgun.application.compose")
    id("com.android.vurgun.hilt")
    id("com.android.vurgun.network")
}

android {
    buildFeatures.buildConfig = true
    hilt.enableAggregatingTask = true
}

dependencies {}
