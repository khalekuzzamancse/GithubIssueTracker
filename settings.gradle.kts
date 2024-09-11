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

val featureModules= listOf(
    ":feature",
    ":feature:issue_list",":feature:issue_list:data",":feature:issue_list:domain",":feature:issue_list:ui",":feature:issue_list:di_container",
    ":feature:issue_details",":feature:issue_details:data",":feature:issue_details:domain",":feature:issue_details:ui",":feature:issue_details:di_container"
)

rootProject.name = "GitHub Issue Tracker"
include(applicationsModules+featureModules)
include(":core:network")

