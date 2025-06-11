plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("android")
    kotlin("kapt")
}

kapt {
    generateStubs = true
    useBuildCache = false
}

android {

    namespace = "com.ghn.cocknovel"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ghn.cocknovel"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = 35
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.toString()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("platform") {
            //将系统签名文件platform.keystore 放在projectName/app/ 目录下
            storeFile = file("Cocknovel.jks")
            storePassword = "Cocknovel" // 对应-srcstorepass
            keyAlias = "Cocknovel" //对应-name
            keyPassword = "Cocknovel" // 对应-pass
//            isV1SigningEnabled = true
//            isV2SigningEnabled = true
        }
    }
    buildTypes {
        val signConfig = signingConfigs.getByName("platform")
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signConfig
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"

            )
            signingConfig = signConfig
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
    }
//    packagingOptions {
//        exclude("AndroidManifest.xml")
//    }
//    dexOptions {
//        javaMaxHeapSize = "4g"
//    }
}

dependencies {

    implementation(project(mapOf("path" to ":CommonModule")))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.test.junit)
    implementation(libs.androidx.junit)
    implementation(libs.androidx.espresso)
    implementation(libs.github.titlebar)
    implementation(libs.github.xbanner)
    implementation(libs.github.xxPermissions)
    kapt (libs.apt)

    // ExoPlayer 核心库
    implementation("com.google.android.exoplayer:exoplayer-core:2.15.0")
    // ExoPlayer UI 控件库
    implementation("com.google.android.exoplayer:exoplayer-ui:2.15.0")
    // ExoPlayer 广告插入扩展库
    implementation("com.google.android.exoplayer:extension-ima:2.15.0")
    implementation("com.google.android.exoplayer:exoplayer-hls:2.15.0")
    implementation("io.github.cymchad:BaseRecyclerViewAdapterHelper4:4.1.4")
}

