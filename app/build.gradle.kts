@file:Suppress("MagicNumber", "SpellCheckingInspection")

import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

val envReleaseNote: String = System.getenv("RELEASE_NOTE") ?: "LOCAL_BUILD"

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
}

detekt {
    allRules = true
    config = files("detekt-config.yml")
    buildUponDefaultConfig = true
}

ktlint {
    reporters {
        reporter(ReporterType.HTML)
        reporter(ReporterType.CHECKSTYLE)
    }
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.alaaeddinalbarghoth.testcicdapp"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            firebaseAppDistribution {
                groups = "QA"
            }
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            firebaseAppDistribution {
                groups = "QA"
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        // allWarningsAsErrors = true
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-rc02"
    }
    packagingOptions {
        resources {
            excludes += "/META-INFinstabug_comment_hint_bug/{AL2.0,LGPL2.1}"
            excludes += "META-INF/*"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.compose.ui:ui:1.2.0-rc02")
    implementation("androidx.compose.material:material:1.2.0-rc02")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.0-rc02")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0-rc02")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.0-rc02")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.0-rc02")
}
