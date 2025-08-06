plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.kavaarch.kavaarch_mvvm_toolkit"
    compileSdk = 36

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            optIn.add("kotlin.RequiresOptIn")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.google.code.gson:gson:2.13.1")
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.2")
    api("androidx.lifecycle:lifecycle-livedata-ktx:2.9.2")
    api("androidx.activity:activity-ktx:1.10.1")
}

group = "com.github.ankit295-ops"  // Replace with your GitHub username
version = "1.0.4"