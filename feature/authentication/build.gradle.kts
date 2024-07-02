plugins {
    id("moviematcher.android.feature")
    id("moviematcher.android.library.compose")
}

android {
    namespace = "com.moviematcher.authentication"
}

dependencies {
    implementation(libs.play.services.auth)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.auth.ktx)
}