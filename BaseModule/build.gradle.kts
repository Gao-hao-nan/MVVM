plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
}
android {
    namespace="com.example.basemodel"
    compileSdk = 35
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":CapturePacketModule"))
    implementation(project(":NetworkModule"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.test.junit)
    implementation(libs.androidx.junit)
    implementation(libs.androidx.espresso)
    implementation(libs.dialogs.core)
    implementation(libs.dialogs.lifecycle)
    implementation(libs.github.xpopup)
    implementation (libs.github.easywindow)


}