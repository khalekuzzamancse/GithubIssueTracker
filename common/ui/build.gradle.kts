plugins {
    alias(libs.plugins.module.ui)
}

android {
    namespace = "common.ui"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.jetpackCompose)
    implementation(libs.coil.compose.v270)

}