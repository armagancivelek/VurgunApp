plugins {
    id("com.android.vurgun.library")
    id("com.android.vurgun.library.compose")
    id("com.android.vurgun.hilt")
}

android {
    namespace = "com.android.vurgun.common_ui"
}

dependencies {

    // module import
    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // material3 templates
    implementation(libs.androidx.material3.android)

    // compose shimmer library
    implementation(libs.compose.shimmer)

    // material-3-extended
    implementation(libs.androidx.compose.material.iconsExtended)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
