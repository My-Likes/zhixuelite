plugins {
    alias(libs.plugins.zhixuelite.android.library)
    alias(libs.plugins.zhixuelite.android.compose)
}

android {
    namespace = "com.zhixue.lite.core.designsystem"
}

dependencies {
    implementation(libs.accompanist.placeholder)
    implementation(libs.androidx.compose.material.ripple)
    implementation(libs.coil.compose)
}