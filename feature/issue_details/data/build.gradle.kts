plugins {
    alias(libs.plugins.module.nonUi)
    alias(libs.plugins.kotlinxSerialization)
}

android {
    namespace = "issue_details.data"


}

dependencies {
    implementation(projects.core.network)//for fetching from remote API
    implementation(projects.feature.issueDetails.domain)
    implementation(libs.ktor.serialization.kotlinx.json)//For Json serialization
}