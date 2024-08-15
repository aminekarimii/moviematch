plugins {
    id("moviematcher.android.feature")
    id("moviematcher.android.library.compose")
}

android {
    namespace = "com.moviematcher.session"
}

dependencies {
    implementation(libs.io.coil)
}