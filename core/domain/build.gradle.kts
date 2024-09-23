plugins {
    id("moviematcher.android.library")
}

android {
    namespace = "com.moviematcher.domain"
}

dependencies {
    api(libs.bundles.io)
}