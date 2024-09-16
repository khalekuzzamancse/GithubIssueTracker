@file:Suppress("UnUsed")

import org.gradle.api.JavaVersion

object Constants {
    const val COMPILE_SDK = 34
    const val MIN_SDK = 24
    val SOURCE_COMPATIBILITY = JavaVersion.VERSION_17
    val TARGET_SOURCE_COMPATIBILITY = JavaVersion.VERSION_17
    const val JVM_TARGET="17"

}