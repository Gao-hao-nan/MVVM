plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}
kapt {
    generateStubs = true
    useBuildCache = false
}
android {
    compileSdk = 35
    namespace = "cn.coderpig.cp_network_capture"

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
    implementation(project(":RouterModule"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.okhttp.okhttp4)
    implementation(libs.retrofit.retrofit2.gson)
    implementation(libs.router)
    kapt (libs.apt)
}
