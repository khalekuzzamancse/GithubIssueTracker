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
val coreModules= listOf(
    ":core",
)
val featureModules= listOf(
    ":feature",
    "feature:issue_list"
)

rootProject.name = "GitHub Issue Tracker"
include(applicationsModules+coreModules+featureModules)
include(":feature:mylibrary")
include(":core:network")
