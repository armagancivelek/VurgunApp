import com.android.vurgun.convention.utils.implementation
import com.android.vurgun.convention.utils.ksp
import com.android.vurgun.convention.utils.kspAndroidTest
import com.android.vurgun.convention.utils.kspTest
import com.android.vurgun.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val hiltCompiler = "hilt.compiler"
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                implementation(libs.findLibrary("hilt.android").get())
                ksp(libs.findLibrary(hiltCompiler).get())
                kspAndroidTest(libs.findLibrary(hiltCompiler).get())
                kspTest(libs.findLibrary(hiltCompiler).get())
                implementation(libs.findLibrary("androidx.hilt.navigation.compose").get())
            }
        }
    }
}
