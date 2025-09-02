import com.android.build.gradle.LibraryExtension
import com.android.vurgun.convention.extensions.configureAndroidCompose
import com.android.vurgun.convention.utils.implementation
import com.android.vurgun.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply(plugin = "com.android.library")
                apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            }
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)

            dependencies {
                implementation(libs.findLibrary("androidx-compose-material3").get())
                implementation(libs.findLibrary("androidx-compose-material3-windowSizeClass").get())
            }
        }
    }
}
