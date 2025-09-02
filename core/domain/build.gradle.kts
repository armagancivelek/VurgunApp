plugins {
    id("com.android.vurgun.library")
    id("com.android.vurgun.hilt")
    id("com.android.vurgun.network")
}

android {
    namespace = "com.android.vurgun.domain"
}

dependencies {
    // Module dependency
    implementation(project(":core:network"))
    implementation(project(":core:common"))

    implementation(libs.lumberjack.log)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
