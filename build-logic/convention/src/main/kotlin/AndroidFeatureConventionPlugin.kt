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

                add("implementation", versionCatalog().findLibrary("koin.android").get())
                add("implementation", versionCatalog().findLibrary("koin.androidx.compose").get())

                add(
                    "implementation",
                    versionCatalog().findLibrary("androidx.lifecycle.runtime.ktx").get()
                )
                add(
                    "implementation",
                    versionCatalog().findLibrary("androidx.lifecycle.runtime.compose").get()
                )
                add("implementation", project(":core:domain"))
                add("implementation",
                    versionCatalog().findLibrary("kotlinx.datetime").get()
                )
            }
        }
    }
}