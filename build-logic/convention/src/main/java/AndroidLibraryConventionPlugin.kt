import com.android.build.gradle.LibraryExtension
import com.android.vurgun.convention.extensions.configureKotlinAndroid
import com.android.vurgun.convention.extensions.defaultSecrets
import com.android.vurgun.convention.utils.androidTestImplementation
import com.android.vurgun.convention.utils.implementation
import com.android.vurgun.convention.utils.kapt
import com.android.vurgun.convention.utils.libs
import com.android.vurgun.convention.utils.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("com.android.vurgun.ktlint")
                apply("com.android.vurgun.detekt")
                apply("kotlin-kapt")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = rootProject.defaultSecrets().getProperty("TARGET_SDK").toInt()

            }
            dependencies {
                //Lifecycle
                implementation(libs.findLibrary("androidx-lifecycle-runtime").get())
                kapt(libs.findLibrary("androidx-lifecycle-compiler").get())
                //Test
                androidTestImplementation(kotlin("test"))
                testImplementation(kotlin("test"))
            }
        }
    }
}