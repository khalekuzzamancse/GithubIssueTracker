plugins {
    alias(libs.plugins.module.ui)
}

android {
    namespace = "feature_issuedetails"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.jetpackCompose)

    implementation(libs.compose.markdown)   //MarkDown Viewer
    implementation(projects.feature.issueDetails.domain)
    implementation(projects.feature.issueDetails.di)
    implementation(projects.common.ui)
    implementation(libs.androidx.lifecycle.viewmodel.compose)//Viewmodel
}