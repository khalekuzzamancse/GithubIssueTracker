plugins {
    alias(libs.plugins.module.nonUi)
    alias(libs.plugins.kotlinxSerialization)

}

android {
    namespace = "core.network"
}

dependencies {
    implementation(libs.bundles.network)

}