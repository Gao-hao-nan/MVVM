plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ghn.commonmodule"
    compileSdk = 35
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    api(project(":BaseModule"))
    api(project(":NetworkModule"))
    api(project(":EventModule"))
    api(project(":RouterModule"))
    api(project(":CapturePacketModule"))
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}