plugins {
    id("moviematcher.android.feature")
    id("moviematcher.android.library.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "com.moviematcher.session"
}

dependencies {
    implementation(libs.io.coil)
    implementation(libs.firebase.auth)
    implementation(libs.zxing.android.embedded)
    implementation(libs.core)
    implementation (libs.gson)
}