plugins {
    alias(libs.plugins.module.nonUi)
}

android {
    namespace = "issue_list.di_container"
}

dependencies {
    implementation(projects.feature.issueList.domain)
    api(projects.feature.issueList.data)
}