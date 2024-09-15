plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinxSerialization)
}

android {
    namespace = "feature_navigation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(project(":core:network"))
    implementation(project(":feature:issue_list:ui"))
    implementation(project(":feature:issue_details:ui"))
    implementation(project(":feature:search"))
    implementation(libs.ktor.serialization.kotlinx.json) //For Json serialization
    implementation(libs.androidx.navigation.compose)//For navigation
    implementation(libs.windowSize)//window size
    implementation(libs.androidx.lifecycle.viewmodel.compose)//Viewmodel

    implementation("androidx.compose.material3.adaptive:adaptive:1.1.0-alpha02")
    implementation("androidx.compose.material3.adaptive:adaptive-layout:1.1.0-alpha02")
    implementation("androidx.compose.material3.adaptive:adaptive-navigation:1.1.0-alpha02")
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.0.0-alpha07")

}