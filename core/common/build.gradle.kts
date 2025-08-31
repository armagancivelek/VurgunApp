plugins {
    id("com.android.vurgun.library")
    id("com.android.vurgun.hilt")
}
android {
    namespace = "com.android.vurgun.common"
}

dependencies {
    implementation(libs.timber)
    implementation(libs.lumberjack.log)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
