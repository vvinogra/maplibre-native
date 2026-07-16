plugins {
    id("com.android.application")
}

// Build the dependency first with:
//   ./gradlew :MapLibreAndroid:assembleMultiBackendDebug
val multiBackendAar = rootProject.file(
    "MapLibreAndroid/build/outputs/aar/MapLibreAndroid-multiBackend-debug.aar"
)

android {
    namespace = "org.maplibre.android.multibackendsample"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.maplibre.android.multibackendsample"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        ndk {
            abiFilters += listOf("arm64-v8a", "x86_64")
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(files(multiBackendAar))

    // The local .aar carries no POM, so the runtime dependencies that
    // MapLibreAndroid itself declares need to be repeated here.
    implementation(libs.maplibreJavaGeoJSON)
    implementation(libs.maplibreGestures)
    implementation(libs.maplibreJavaTurf)
    implementation(libs.supportAnnotations)
    implementation(libs.supportFragmentV4)
    implementation(libs.okhttp3)
    implementation(libs.timber)
    implementation(libs.interpolator)

    implementation(libs.supportDesign)
}
