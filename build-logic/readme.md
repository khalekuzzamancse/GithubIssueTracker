# Convention Plugin Overview

A convention plugin is a reusable build logic module in Gradle. It provides several benefits:
- **Encapsulates common build configurations**: Eliminates duplication and maintains consistency across modules.
- **Simplifies project structure**: Reduces complexity in individual module build files.
- **Improves maintainability**: Makes it easier to manage and apply updates across all modules.
- **Boosts build performance**: Modularized build logic speeds up the build process.
- **Scales with larger projects**: Ideal for projects with many modules or components.


# Implementation Guide

- Create a separate directory called `buid-logic` as direct child of `root project`
- Add the `build.gradle.kts`
  - include the this directory as `Composite Build` in root project `setting.gradle` under the `pluginManagement`block , example as
  `includeBuild("build-logic")`
- Create directory `src/main/kotlin` and don't create any package in this `direcotory`
- Then define the `Conventional plugin` as Kotlin class (do not include them under package)

- More read: [Herding Elephants](https://developer.squareup.com/blog/herding-elephants/) , [idiomatic-gradle](https://github.com/jjohannes/idiomatic-gradle)