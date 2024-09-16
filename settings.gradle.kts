pluginManagement {
    includeBuild("build-logic")//build-logic as a Composite Build
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
/*
 * Type-Safe Project Accessors, a feature introduced in Gradle 7.0 that allows you to reference project dependencies
 * in a type-safe manner without relying on string-based project paths like project(":x") as implement(projects.x)
 */
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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

rootProject.name = "GitHub_Issue_Tracker"
include(applicationsModules+commonModules+featureModules)
include(":core:network")

