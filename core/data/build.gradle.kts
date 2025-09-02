plugins {
    id("com.android.vurgun.library")
    id("com.android.vurgun.hilt")
}

android {
    namespace = "com.android.vurgun.data"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)

    implementation(libs.androidx.datastore.preferences)
}
