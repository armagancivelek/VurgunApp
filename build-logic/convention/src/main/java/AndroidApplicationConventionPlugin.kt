import com.android.build.api.dsl.ApplicationExtension
import com.android.vurgun.convention.extensions.configureKotlinAndroid
import com.android.vurgun.convention.extensions.registerPrePushTask
import com.android.vurgun.convention.utils.androidTestImplementation
import com.android.vurgun.convention.utils.implementation
import com.android.vurgun.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.android.vurgun.ktlint")
                apply("com.android.vurgun.detekt")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
            }

            dependencies {
                // AndroidX
                implementation(libs.findLibrary("androidx.core.splashscreen").get())

                // Compose
                implementation(libs.findLibrary("androidx.compose.runtime.tracing").get())
                implementation(libs.findLibrary("androidx.compose.material3.windowSizeClass").get())
                implementation(libs.findLibrary("androidx.navigation.compose").get())
                implementation(libs.findLibrary("androidx.material3.android").get())
                implementation(libs.findLibrary("androidx.monitor").get())
                implementation(libs.findLibrary("androidx.test.ext").get())
                androidTestImplementation(libs.findLibrary("junit").get())
                // Modules
                implementation(project(":core:common-ui"))
                implementation(project(":core:common"))
            }
            registerPrePushTask()
        }
    }
}
