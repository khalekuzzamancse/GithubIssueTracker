pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
val applicationsModules= listOf(":application")
val commonModules= listOf(
    ":common",":common:misc",":common:ui"
)

val featureModules= listOf(
    ":feature",
    ":feature:issue_list",":feature:issue_list:data",":feature:issue_list:domain",":feature:issue_list:ui",":feature:issue_list:di",
    ":feature:issue_details",":feature:issue_details:data",":feature:issue_details:domain",":feature:issue_details:ui",":feature:issue_details:di",
    ":feature:search",
    ":feature:navigation"
)

rootProject.name = "GitHub Issue Tracker"
include(applicationsModules+commonModules+featureModules)
include(":core:network")

