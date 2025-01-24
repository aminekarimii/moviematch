plugins {
    id("moviematcher.android.library")
}

android {
    namespace = "com.moviematcher.domain"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    api(libs.bundles.io)
}