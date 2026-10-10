import com.google.firebase.appdistribution.gradle.firebaseAppDistribution
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.google.firebase.appdistribution")
}

// Local-only credentials live in keystore.properties (git-ignored). In CI these same
// keys are provided as environment variables (from GitHub Secrets) instead.
val keystoreProperties =
    Properties().apply {
        val file = rootProject.file("keystore.properties")
        if (file.exists()) {
            file.inputStream().use { load(it) }
        }
    }

fun secret(key: String): String? = keystoreProperties.getProperty(key) ?: System.getenv(key)

android {
    namespace = "com.example.calculator"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.calculator"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val releaseKeystore = secret("KEYSTORE_FILE")

    signingConfigs {
        create("release") {
            if (releaseKeystore != null) {
                storeFile = rootProject.file(releaseKeystore)
                storePassword = secret("KEYSTORE_PASSWORD")
                keyAlias = secret("KEY_ALIAS")
                keyPassword = secret("KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            // Sign only when the keystore is provided (CI); local builds stay unsigned.
            if (releaseKeystore != null) {
                signingConfig = signingConfigs.getByName("release")
            }
            firebaseAppDistribution {
                appId = secret("FIREBASE_APP_ID") ?: ""
                serviceCredentialsFile =
                    secret("FIREBASE_SERVICE_ACCOUNT_FILE")?.let { rootProject.file(it).absolutePath } ?: ""
                groups = "testers"
                releaseNotes = "Automated release from CI"
            }
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
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
