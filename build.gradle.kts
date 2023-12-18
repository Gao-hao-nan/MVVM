// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
//    ext.kotlin_version="1.7.0"
    extra["kotlin_version"]="1.7.0"
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")

    }
}
//@Suppress("DSL_SCOPE_VIOLATION")
//plugins {
//    id ("com.android.application") version "8.1.2" apply false
//    id ("com.android.library") version "8.1.2" apply false
//    id ("org.jetbrains.kotlin.android") version "1.9.0" apply false
//}

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
}
true