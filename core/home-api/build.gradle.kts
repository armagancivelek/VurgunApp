plugins {
    id("com.android.vurgun.feature")
    id("com.android.vurgun.network")
    id("com.android.vurgun.library.compose")
}
android {
    namespace = "com.android.vurgun.home.api"
}

dependencies {

    // module dependency
    implementation(project(":core:network"))

    // compose shimmer library
    implementation(libs.compose.shimmer)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.paging.compose)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
