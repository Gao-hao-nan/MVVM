plugins {
    id ("com.android.library")
    kotlin("android")
}

android {
    compileSdk=31
    namespace=("cn.coderpig.cp_network_capture")
//    defaultConfig {
//        minSdk 21
//        targetSdk 33
//        versionCode 1
//        versionName "0.0.13"
//    }

//    buildTypes {
//        release {
//            minifyEnabled false
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
//    implementation 'androidx.core:core-ktx:1.3.2'
//    implementation 'androidx.appcompat:appcompat:1.3.0'
//    implementation 'com.google.android.material:material:1.4.0'
    implementation(libs.okhttp.okhttp4)
    implementation(libs.retrofit.retrofit2.gson)
//    implementation 'com.squareup.okhttp3:okhttp:3.14.2'
//    implementation 'com.google.code.gson:gson:2.6.2'
}