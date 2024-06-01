plugins {
    id("moviematcher.android.application")
    id("moviematcher.android.application.compose")
}

android {
    namespace = "com.moviematcher.app"

    defaultConfig {
        applicationId = "com.moviematcher.app"

        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:designsystem"))

    // Core
    implementation(libs.androidx.core.ktx)

    // Activity
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)
}