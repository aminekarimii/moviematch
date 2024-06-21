plugins {
    id("moviematcher.android.library")
    id("moviematcher.android.library.compose")
}

android {
    namespace = "com.moviematcher.designsystem"
}

dependencies {
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.material3)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.ui.constraintlayout)
    debugApi(libs.androidx.ui.tooling)
}