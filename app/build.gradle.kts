plugins {
    id("com.android.vurgun.application")
    id("com.android.vurgun.application.compose")
    id("com.android.vurgun.hilt")
    id("com.android.vurgun.network")
    alias(libs.plugins.gms)
}

android {
    buildFeatures.buildConfig = true
    hilt.enableAggregatingTask = true
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:data"))
    implementation(project(":feature:home"))
    implementation(project(":feature:slips"))
    implementation(project(":feature:current-slip"))
    // Firebase
    val firebaseBom = platform(libs.firebase.bom)
    implementation(firebaseBom)
    implementation(libs.firebase.analytics)
}
