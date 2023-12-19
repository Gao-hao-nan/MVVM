plugins {
    id("com.android.application")
//    id ("org.jetbrains.kotlin.android")
    id("kotlin-android")
    kotlin("android")
    kotlin("kapt")
//    kotlin("android-extensions")
}
//apply plugin: "kotlin-android-extensions"
//apply plugin: "kotlin-android"
//apply plugin: "kotlin-kapt"


kapt {
    generateStubs = true
    useBuildCache = false
}

android {
    namespace = "com.ghn.cocknovel"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.ghn.cocknovel"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()
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
            isV1SigningEnabled = true
            isV2SigningEnabled = true
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
    packagingOptions {
        exclude("AndroidManifest.xml")
    }
//    dataBinding {
//        enabled = true
//    }
//    viewBinding {
//        enabled = true
//    }

}

dependencies {
    implementation(project(mapOf("path" to ":BaseModule")))
    implementation(project(mapOf("path" to ":NetworkModule")))
//    implementation("androidx.core:core-ktx:1.8.0")
//    implementation("androidx.appcompat:appcompat:1.5.1")
//    implementation("com.google.android.material:material:1.6.1")
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//    implementation("androidx.navigation:navigation-fragment-ktx:2.4.1")
//    implementation("androidx.navigation:navigation-ui-ktx:2.4.1")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
//    // 标题栏框架
//    implementation("com.github.getActivity:TitleBar:10.0")
//    implementation("com.github.xiaohaibin:XBanner:androidx_v1.2.6")

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
    implementation("com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.7")
    implementation(libs.github.xxPermissions)
}
//android.applicationVariants.all {
//    variant ->
//        variant.outputs.all { output ->
////            def date = new Date().format("yyMMdd", TimeZone.getTimeZone("GMT+08"))
//            if (variant.buildType.name == 'debug'){
//                output.outputFileName = "鲸鱼阅读_debug.apk"
//            }else if (variant.buildType.name == 'release'){
//                output.outputFileName = "鲸鱼阅读_release.apk"
//            }
//        }
//}
