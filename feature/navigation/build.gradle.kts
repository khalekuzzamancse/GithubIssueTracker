plugins {
    alias(libs.plugins.module.ui)
    alias(libs.plugins.kotlinxSerialization)//for navigation
}

android {
    namespace = "feature_navigation"

}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.jetpackCompose)
    implementation(libs.bundles.materialAdaptive)
    implementation(libs.bundles.navigation)

    implementation(projects.feature.issueList.ui)
    implementation(projects.feature.issueDetails.ui)
    implementation(projects.feature.search)
    implementation(projects.common.ui)

    
    implementation(libs.windowSize)//window size
    implementation(libs.androidx.lifecycle.viewmodel.compose)//Viewmodel


}