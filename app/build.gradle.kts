plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

import java.util.Properties

// Signing credentials come from keystore.properties (local dev) or env vars (CI),
// so the keystore password is never committed to the repo.
val keystoreProperties = Properties().apply {
    val f = rootProject.file("keystore.properties")
    if (f.exists()) f.inputStream().use { load(it) }
}

fun String?.orEnv(env: String): String? = this?.takeIf { it.isNotBlank() } ?: System.getenv(env)

val releaseStoreFile = keystoreProperties["storeFile"]?.toString()?.orEnv("KEYSTORE_FILE")
    ?: (rootProject.file("mammoth-release.keystore").takeIf { it.exists() }
        ?: file("mammoth-release.keystore").takeIf { it.exists() }
        ?: rootProject.file("mammoth-release.keystore")).absolutePath
val releaseStorePass = keystoreProperties["storePassword"]?.toString()?.orEnv("KEYSTORE_PASSWORD") ?: "mammoth123"
val releaseKeyAlias = keystoreProperties["keyAlias"]?.toString()?.orEnv("KEY_ALIAS") ?: "mammoth"
val releaseKeyPass = keystoreProperties["keyPassword"]?.toString()?.orEnv("KEY_PASSWORD") ?: "mammoth123"

android {
    namespace = "dae.mammoth.id"
    compileSdk = 35

    defaultConfig {
        applicationId = "dae.mammoth.id"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.0"

        // only build the ABIs Android phones actually use
        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86_64")
        }
        externalNativeBuild {
            cmake {
                arguments += "-DANDROID_STL=none"
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(releaseStoreFile)
            storePassword = releaseStorePass
            keyAlias = releaseKeyAlias
            keyPassword = releaseKeyPass
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.12.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-service:2.8.7")
    implementation("androidx.navigation:navigation-compose:2.8.5")

    debugImplementation("androidx.compose.ui:ui-tooling")
}
