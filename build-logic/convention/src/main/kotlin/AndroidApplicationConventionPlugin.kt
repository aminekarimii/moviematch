import com.android.build.api.dsl.ApplicationExtension
import com.moviematcher.convention.configureAndroidCompose
import com.moviematcher.convention.configureKotlinAndroid
import com.moviematcher.convention.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                with(defaultConfig) {
                    targetSdk = versionCatalog().findVersion("targetSdk").get().toString().toInt()
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }
                buildTypes {
                    release {
                        isMinifyEnabled = true
                        isShrinkResources = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
                val extension = extensions.getByType<ApplicationExtension>()
                configureAndroidCompose(extension)
            }
            dependencies {
                add("implementation", versionCatalog().findLibrary("koin.android").get())
                add("implementation", versionCatalog().findLibrary("koin.androidx.compose").get())
            }
        }
    }
}