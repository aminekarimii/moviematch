import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}
group = "com.moviematcher.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "moviematcher.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "moviematcher.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "moviematcher.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "moviematcher.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "moviematcher.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("kotlinMultiplatformLibrary") {
            id = "moviematcher.kmp.library"
            implementationClass = "KotlinMultiplatformLibraryConventionPlugin"
        }
        register("kotlinMultiplatformCompose") {
            id = "moviematcher.kmp.compose"
            implementationClass = "ComposeMultiplatformConventionPlugin"
        }
        register("kotlinMultiplatformFeature") {
            id = "moviematcher.kmp.feature"
            implementationClass = "KotlinMultiplatformFeatureConventionPlugin"
        }
    }
}