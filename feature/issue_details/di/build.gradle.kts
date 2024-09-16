plugins {
    alias(libs.plugins.module.nonUi)
}

android {
    namespace = "issue_details.di_container"
}

dependencies {
    implementation(projects.feature.issueDetails.domain)
    api(projects.feature.issueDetails.data)
}