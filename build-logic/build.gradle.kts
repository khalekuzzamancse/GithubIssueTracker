import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
    compileOnly("com.android.tools.build:gradle:8.1.1")
}

gradlePlugin {
    plugins {
        register("uiModule") {
            id = "module.ui"
            implementationClass = "UiModuleConventionPlugin"
        }
        register("nonUIModule") {
            id = "module.non.ui"
            implementationClass = "NonUIModuleConventionPlugin"
        }


    }
}
/*
Define the
kotlinOptions {
        jvmTarget = "1.8"
    }
 */
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}