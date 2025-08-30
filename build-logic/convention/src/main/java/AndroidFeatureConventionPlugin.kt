import com.android.vurgun.convention.utils.androidTestImplementation
import com.android.vurgun.convention.utils.implementation
import com.android.vurgun.convention.utils.libs
import com.android.vurgun.convention.utils.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.vurgun.library")
                apply("com.android.vurgun.hilt")
                apply("com.android.vurgun.ktlint")
                apply("com.android.vurgun.detekt")
            }

            dependencies {
                // Test
                testImplementation(kotlin("test"))
                androidTestImplementation(kotlin("test"))

                // Libs
                implementation(libs.findLibrary("timber").get())
                implementation(libs.findLibrary("lumberjack.log").get())
                implementation(libs.findLibrary("androidx.navigation.compose").get())
                implementation(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                implementation(libs.findLibrary("androidx.lifecycle.viewModelCompose").get())

                implementation(libs.findLibrary("kotlinx.coroutines.android").get())
                implementation(libs.findLibrary("kotlinx.collections.immutable").get())
            }
        }
    }
}
