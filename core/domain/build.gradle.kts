plugins {
    id("moviematcher.android.library")
}

android {
    namespace = "com.moviematcher.domain"
}

dependencies {
    implementation(libs.koin.android)

    api(libs.bundles.io)
}