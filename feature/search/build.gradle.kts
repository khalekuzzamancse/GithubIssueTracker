plugins {
    alias(libs.plugins.module.ui)
}

android {
    namespace = "feature_search"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.jetpackCompose)

    implementation(projects.feature.issueList.ui)
    implementation(projects.feature.issueList.domain)
    implementation(projects.feature.issueList.di)
    implementation(projects.common.ui)
    implementation(libs.androidx.lifecycle.viewmodel.compose)//Viewmodel
}