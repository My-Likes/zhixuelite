plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.zhixuelite.android.library)
    alias(libs.plugins.zhixuelite.android.hilt)
}

android {
    namespace = "com.zhixue.lite.core.network"
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.coil.kt)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.core)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.kotlinx.serialization)
}