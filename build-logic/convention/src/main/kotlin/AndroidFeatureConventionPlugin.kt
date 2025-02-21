import com.android.build.gradle.LibraryExtension
import com.moviematcher.convention.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("moviematcher.android.library")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "com.moviematcher.testing.NiaTestRunner"
                }
            }

            dependencies {
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:domain"))

                add("implementation", platform(versionCatalog().findLibrary("koin.bom").get()))
                add("implementation", versionCatalog().findLibrary("koin.compose").get())
                add("implementation", versionCatalog().findLibrary("koin.compose.viewmodel").get())

                add(
                    "implementation",
                    versionCatalog().findLibrary("androidx.navigation").get()
                )

                add("implementation",
                    versionCatalog().findLibrary("kotlinx.datetime").get()
                )
            }
        }
    }
}