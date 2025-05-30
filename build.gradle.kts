buildscript {
    dependencies {
        classpath (libs.kotlin.gradle.plugin)
        classpath (libs.hilt.android.gradle.plugin)
        classpath (libs.plugin)

    }
}
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
}
true