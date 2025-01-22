import java.util.Properties

plugins {
    id("moviematcher.android.library")
}

android {
    namespace = "com.moviematcher.data"

    buildFeatures {
        buildConfig = true
    }

    val keysFile = rootProject.file("keys.properties")
    val properties = Properties().apply {
        load(keysFile.inputStream())
    }

    defaultConfig {
        buildConfigField("String", "TMDB_KEY_API", "\"${properties["TMDB_KEY_API"]}\"")
    }
}

dependencies {
    implementation(project(":core:domain"))

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    implementation(libs.firebase.auth)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.auth.ktx)

}