plugins {
    id("moviematcher.android.library")
}

android {
    namespace = "com.moviematcher.data"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.koin.android)

    implementation(libs.firebase.auth)
    implementation(libs.firebase.auth.ktx)
}