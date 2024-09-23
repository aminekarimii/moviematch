plugins {
    id("moviematcher.android.feature")
    id("moviematcher.android.library.compose")
}

android {
    namespace = "com.moviematcher.feature.matching"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    implementation(libs.io.coil)
    implementation(libs.lottie.compose)
}