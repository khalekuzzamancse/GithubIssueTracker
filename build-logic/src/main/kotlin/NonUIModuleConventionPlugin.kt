

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class NonUIModuleConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")

            extensions.configure<LibraryExtension> {

                compileSdk = Constants.COMPILE_SDK

                defaultConfig {
                    minSdk = Constants.MIN_SDK

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
                compileOptions {
                    sourceCompatibility = Constants.SOURCE_COMPATIBILITY
                    targetCompatibility = Constants.TARGET_SOURCE_COMPATIBILITY
                }
//                kotlinOptions { //Can not define here
//                    jvmTarget = "1.8"
//                }


            }
            dependencies{
               
            }
        }
    }
}
