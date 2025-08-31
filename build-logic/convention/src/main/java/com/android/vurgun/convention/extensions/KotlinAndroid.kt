package com.android.vurgun.convention.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.vurgun.convention.utils.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Properties

internal fun Project.configureKotlinAndroid(
    commonExtension: ApplicationExtension,
) {
    commonExtension.apply {
        namespace = rootProject.defaultSecrets().getProperty("APPLICATION_ID")
        compileSdk = defaultSecrets().getProperty("COMPILE_SDK").toInt()

        defaultConfig {
            applicationId = defaultSecrets().getProperty("APPLICATION_ID")
            minSdk = defaultSecrets().getProperty("MIN_SDK").toInt()
            targetSdk = defaultSecrets().getProperty("TARGET_SDK").toInt()
            versionCode = computedVersionCode()
            versionName = defaultSecrets().getProperty("VERSION_NAME")
            setProperty("archivesBaseName", "$namespace-$versionName-$versionCode-${gitCommitCount()}")
            ndk {
                debugSymbolLevel = "FULL"
            }

            testInstrumentationRunner = defaultSecrets().getProperty("TEST_RUNNER")
            vectorDrawables {
                useSupportLibrary = true
            }

            javaCompileOptions {
                annotationProcessorOptions {
                    arguments["room.schemaLocation"] = "$projectDir/schemas"
                }
            }
            buildConfigField("String", "VERSION_NAME", "\"${defaultSecrets().getProperty("VERSION_NAME")}\"")
        }

        signingConfigs {
            create("release") {
                // Config file
//            val properties = Properties()
//            properties.load(FileInputStream(file("sign/keystore.config")))
//
//            // Set config
//            keyAlias = properties["keyAlias"].toString()
//            keyPassword = properties["keyPassword"].toString()
//            storeFile = file(properties["storeFile"].toString())
//            storePassword = properties["storePassword"].toString()
            }
        }

        buildTypes {
            debug {
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
                isMinifyEnabled = defaultSecrets().getProperty("MINIFY_ENABLED_DEBUG").toBoolean()
                isDebuggable = true
                versionNameSuffix = ".${gitCommitCount()}-SNAPSHOT"
            }
            release {
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
                isMinifyEnabled = defaultSecrets().getProperty("MINIFY_ENABLED_RELEASE").toBoolean()
                isShrinkResources = true
                isDebuggable = false
                versionNameSuffix = ".${gitCommitCount()}"
                signingConfig = signingConfigs.findByName("release")
            }
        }

        flavorDimensions += "version"
        productFlavors {
            create("prod") {
                dimension = "version"
            }
            create("dev") {
                dimension = "version"
                applicationIdSuffix = ".dev"
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                val warningsAsErrors: String? by project
                allWarningsAsErrors.set(warningsAsErrors.toBoolean())
                jvmTarget.set(JvmTarget.JVM_17)

                freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
            }
        }

    }
    configureKotlin()
    dependencies {
        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = rootProject.defaultSecrets().getProperty("COMPILE_SDK").toInt()

        defaultConfig {
            minSdk = rootProject.defaultSecrets().getProperty("MIN_SDK").toInt()
        }

        flavorDimensions += "version"
        productFlavors {
            create("prod") {
                dimension = "version"
            }
            create("dev") {
                dimension = "version"
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }

        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                val warningsAsErrors: String? by project
                allWarningsAsErrors.set(warningsAsErrors.toBoolean())
                jvmTarget.set(JvmTarget.JVM_17)

                freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
            }
        }

    }
    configureKotlin()
    dependencies {
        add("coreLibraryDesugaring", libs.findLibrary("android.desugarJdkLibs").get())
    }
}

/**
 * Configure base Kotlin options for JVM (non-Android)
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        // Up to Java 11 APIs are available through desugaring
        // https://developer.android.com/studio/write/java11-minimal-support-table
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    configureKotlin()
}

/**
 * Configure base Kotlin options
 */
private fun Project.configureKotlin() {
    with(extensions.getByType<KotlinAndroidProjectExtension>()) {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)

            val warningsAsErrors: String? by project
            allWarningsAsErrors.set(warningsAsErrors.toBoolean())
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
            freeCompilerArgs.add("-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
            freeCompilerArgs.add("-opt-in=kotlinx.coroutines.FlowPreview")
        }
    }
}

internal fun Project.   defaultSecrets(): Properties {
    val keystoreFile = this.rootProject.file("secrets.properties")
    val properties = Properties()
    properties.load(keystoreFile.inputStream())
    return properties
}

/**
 * computedVersionCode()
 * do not name this to getVersionCode. getVersionCode conflicts with the automatic getter of versionCode
 * version code is an int a value between 0 and max int value 2147483647 is expected.
 * This function returns at int in yyyMMddHH format
 * For example, 2022061121 for 11 June 2022 between 21:00 to 21:59
 * This gives a new versioncode for every different hour of day and same code within same hour of hour of day
 * Max int value is 2147483647. So after year 2147 it will overflow to -ve values.
 * max value in year 2147 will be 2147121223 so Lot of scope of manually incrementing up-to 2147483647  will be there.
 * @return an int corresponding to current hour in yyyyMMddHH format
 */
fun computedVersionCode(): Int {
    val date = Date()
    val formattedDate = SimpleDateFormat("yyyyMMddHH", Locale.getDefault()).format(date)
    return formattedDate.toLong().toInt()
}

fun Project.gitCommitCount(): String {
    val os = org.jetbrains.kotlin.org.apache.commons.io.output.ByteArrayOutputStream()
    this.exec {
        commandLine = "git rev-list HEAD --count".split(" ")
        standardOutput = os
    }
    return String(os.toByteArray()).trim()
}
