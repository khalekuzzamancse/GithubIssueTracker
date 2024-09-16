plugins {
    alias(libs.plugins.module.nonUi)
    alias(libs.plugins.kotlinxSerialization)
}

android {
    namespace = "issue_list.data"

}

dependencies {
    implementation(projects.feature.issueList.domain)
    implementation(projects.feature.issueDetails.di)
    implementation(projects.core.network)//for fetching from remote API
    implementation(libs.ktor.serialization.kotlinx.json)//For Json serialization
}