// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
//    ext.kotlin_version="1.7.0"
    extra["kotlin_version"]="2.0.0"
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.38.1")

    }
}
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
}
true