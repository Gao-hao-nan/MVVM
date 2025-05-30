plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

kapt {
    generateStubs = true
}

android {
    compileSdk = 35
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
    namespace = "com.kt.ktmvvm.lib"

}

dependencies {
    api(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)
    implementation(libs.androidx.appcompat)
    implementation(libs.kotlinx.core)
    implementation(libs.kotlinx.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.material)
    implementation(libs.okhttp.okhttp4.logging)
    implementation(libs.okhttp.okhttp4)
    implementation(libs.retrofit.retrofit2)
    api(libs.retrofit.retrofit2.gson)
    implementation(libs.retrofit.retrofit2.scalars)
    implementation(libs.rxjava.adapter)
    api(libs.rxlifecycle.rxlifecycle4.android)
    api(libs.rxlifecycle.rxlifecycle4.components)
    api(libs.github.glide)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.jetbrains.annotations)
    implementation(libs.aliyun.httpdns)
    implementation(libs.jessyan.autosize)
    implementation(libs.dialog.avi.library)
    implementation(libs.dialog.blankj)
    api(libs.github.lqdbrv)
    implementation(libs.gencent.mmkv)
    api(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.refresh.header.classics)

    kapt (libs.apt)
    api(libs.router)
    implementation(libs.multidex)
}