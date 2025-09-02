import com.android.vurgun.convention.utils.androidTestImplementation
import com.android.vurgun.convention.utils.debugImplementation
import com.android.vurgun.convention.utils.implementation
import com.android.vurgun.convention.utils.libs
import com.android.vurgun.convention.utils.releaseImplementation
import com.android.vurgun.convention.utils.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin


class AndroidNetworkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                // Projects
                implementation(libs.findLibrary("retrofit").get())
                implementation(libs.findLibrary("retrofit.converter.kotlinx.serialization").get())
                implementation(libs.findLibrary("retrofit.response.type.keeper").get())
                implementation(libs.findLibrary("kotlinx.serialization.json").get())
                implementation(libs.findLibrary("okhttp3").get())
                implementation(libs.findLibrary("coil").get())
                implementation(libs.findLibrary("coil.okhttp").get())
                // Test
                testImplementation(kotlin("test"))
                androidTestImplementation(kotlin("test"))

                //Chucker
                debugImplementation(libs.findLibrary("chucker").get())
                releaseImplementation(libs.findLibrary("chucker.noop").get())
            }
        }
    }
}
