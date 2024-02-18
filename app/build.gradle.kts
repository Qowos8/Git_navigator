plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("org.jetbrains.kotlin.plugin.serialization") version "2.0.0-Beta2"
}
apply{
    plugin ("dagger.hilt.android.plugin")
    plugin ("com.android.application")
}

android {
    namespace = "com.example.git_navigator"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.git_navigator"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation (libs.material)
    implementation(libs.kotlinx.serialization.json)
    implementation (libs.retrofit)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    implementation (libs.glide)
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.fragment.ktx)
    kapt (libs.androidx.hilt.compiler)
    implementation (libs.google.dagger.hilt.android)
    kapt (libs.google.dagger.compiler)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation (libs.lottie)
    implementation (libs.material.v1110)
    implementation(libs.mpandroidchart)

    implementation (libs.androidx.room.ktx)
    implementation (libs.androidx.room.runtime)

    implementation (libs.kotlinx.coroutines.android.v150)
    implementation (libs.kotlinx.coroutines.core)

}