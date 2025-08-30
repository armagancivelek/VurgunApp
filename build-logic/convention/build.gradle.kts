import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    `kotlin-dsl`
}

group = defaultSecrets().getProperty("BUILD_LOGIC_GROUP")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {

    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
}


gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.android.vurgun.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "com.android.vurgun.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "com.android.vurgun.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "com.android.vurgun.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "com.android.vurgun.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidRoom") {
            id = "com.android.vurgun.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        register("androidHilt") {
            id = "com.android.vurgun.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidKtlint") {
            id = "com.android.vurgun.ktlint"
            implementationClass = "AndroidKtlintConventionPlugin"
        }
        register("androidDetekt") {
            id = "com.android.vurgun.detekt"
            implementationClass = "AndroidDetektConventionPlugin"
        }
        register("androidNetwork") {
            id = "com.android.vurgun.network"
            implementationClass = "AndroidNetworkConventionPlugin"
        }
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

// Secrets
fun defaultSecrets(): Properties {
    val keystoreFile = project.rootProject.file("../secrets.properties")
    val properties = Properties()
    properties.load(keystoreFile.inputStream())
    return properties
}
