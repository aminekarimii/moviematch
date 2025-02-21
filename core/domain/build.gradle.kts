import com.moviematcher.convention.setFrameworkBaseName

plugins {
    id("moviematcher.kmp.library")
}

kotlin {
    setFrameworkBaseName("domain")

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.serialization)
            }
        }
    }
}

android {
    namespace = "com.moviematcher.domain"
}