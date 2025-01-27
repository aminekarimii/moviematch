import com.android.build.api.dsl.ApplicationExtension
import com.moviematcher.convention.configureAndroidCompose
import com.moviematcher.convention.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)

            dependencies {
                add("implementation", platform(versionCatalog().findLibrary("koin.bom").get()))
                add("implementation", versionCatalog().findLibrary("koin.compose").get())
                add("implementation", versionCatalog().findLibrary("koin.compose.viewmodel").get())
            }
        }
    }
}