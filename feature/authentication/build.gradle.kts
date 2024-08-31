plugins {
    id("moviematcher.android.feature")
    id("moviematcher.android.library.compose")
}

android {
    namespace = "com.moviematcher.authentication"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.play.services.auth)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.auth.ktx)
}