plugins {
    id("moviematcher.android.application")
    id("moviematcher.android.application.compose")
    alias(libs.plugins.google.play.services)
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
    implementation(project(":feature:authentication"))
    implementation(project(":feature:session"))

    // Google auth
    implementation(libs.play.services.auth)
    // Firebase auth
    implementation(libs.firebase.auth)

    // Core
    implementation(libs.androidx.core.ktx)

    // Activity
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.navigation)
}