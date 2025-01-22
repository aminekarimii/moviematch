plugins {
    id("moviematcher.android.feature")
    id("moviematcher.android.library.compose")
}

android {
    namespace = "com.moviematcher.feature.matching"
}

dependencies {
    implementation(project(":feature:session"))
    implementation(libs.io.coil)
    implementation(libs.lottie.compose)
}