package com.moviematcher.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = versionCatalog().findVersion("compileSdk").get().toString().toInt()

        defaultConfig {
            minSdk = versionCatalog().findVersion("minSdk").get().toString().toInt()
        }

        compileOptions {
            // Up to Java 11 APIs are available through desugaring
            // https://developer.android.com/studio/write/java11-minimal-support-table
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    dependencies {

        add("implementation", platform(versionCatalog().findLibrary("koin.bom").get()))
        add("implementation", versionCatalog().findLibrary("koin.android").get())
    }
}

