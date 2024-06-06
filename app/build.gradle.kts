plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.zhixuelite.android.application)
    alias(libs.plugins.zhixuelite.android.compose)
    alias(libs.plugins.zhixuelite.android.hilt)
}

android {
    namespace = "com.zhixue.lite"

    defaultConfig {
        applicationId = "com.zhixue.lite"
        versionCode = 1
        versionName = "1.0.0"
        resourceConfigurations += "zh-rCN"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.feature.login)
    implementation(projects.feature.main)
    implementation(projects.feature.report)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.kt)
    implementation(libs.kotlinx.serialization.json)
}