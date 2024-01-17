plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
}

android {
    namespace="com.example.basemodel"
    compileSdk=libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk=libs.versions.minSdk.get().toInt()
        targetSdk=libs.versions.compileSdk.get().toInt()
        testInstrumentationRunner="androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles="consumer-rules.pro"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

//    implementation ("androidx.core:core-ktx:1.8.0")
//    implementation ("androidx.appcompat:appcompat:1.4.1")
//    implementation ("com.google.android.material:material:1.5.0")

//    testImplementation ("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
//    implementation ("com.github.Doonkey:DkFloatingView:1.1.0")
    implementation(project(mapOf("path" to ":NetworkModule")))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.test.junit)
    implementation(libs.androidx.junit)
    implementation(libs.androidx.espresso)
    implementation(libs.dialogs.core)
    implementation(libs.dialogs.lifecycle)
}