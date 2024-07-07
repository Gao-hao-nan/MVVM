plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

//apply plugin: 'kotlin-android'
//apply plugin: 'kotlin-android-extensions'
//apply plugin: 'kotlin-kapt'

kapt {
    generateStubs = true
}

android {
    compileSdk = 34
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"

            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
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
//    kapt(libs.androidx.room.comliler)
    implementation(libs.jetbrains.annotations)
    implementation(libs.aliyun.httpdns)
    implementation(libs.jessyan.autosize)
    implementation(libs.dialog.avi.library)
    implementation(libs.dialog.blankj)
    api(libs.github.lqdbrv)
    implementation(libs.gencent.mmkv)
    api(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation("io.github.scwang90:refresh-header-classics:2.0.5")
}

//    api("com.github.bumptech.glide:glide:4.13.2")
//    //room数据库
//    api("androidx.room:room-runtime:2.4.2")
//    kapt("androidx.room:room-compiler:2.4.2") // Kotlin 使用 kapt
//    api("androidx.room:room-ktx:2.4.2")//Coroutines support for Room 协程操作库
//    implementation("org.jetbrains:annotations:15.0")
//    //httpDns 优化
//    implementation("com.aliyun.ams:alicloud-android-httpdns:2.2.2")
//    //加载中的dialog
//    implementation("com.wang.avi:library:2.1.3")
//    implementation("com.blankj:utilcodex:1.31.0")
    //屏幕适配
//    implementation("me.jessyan:autosize:1.2.1")
    // 权限请求框架
//    api("com.github.getActivity:XXPermissions:16.6")
//    //弹窗
//    api("com.github.li-xiaojun:XPopup:2.9.19")
//    //MMKV
//    api("com.tencent:mmkv:1.3.0")
//    api("com.github.coder-pig:CpNetworkCapture:0.0.11")
//
//    api("com.afollestad.material-dialogs:core:3.1.1")
//    api("com.afollestad.material-dialogs:lifecycle:3.1.1")
//}