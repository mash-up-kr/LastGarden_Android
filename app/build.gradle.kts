plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.mashup.lastgarden"
        minSdk = 23
        targetSdk = 30
        versionCode = Versions.App.major * 1000 +
                Versions.App.feature * 100 +
                Versions.App.regular * 10 +
                Versions.App.minor
        versionName = "${Versions.App.major}.${Versions.App.feature}.${Versions.App.regular}.${Versions.App.minor}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release")  {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
    implementation(project(":base"))

    // Hilt
    implementation(Libs.Hilt.hilt)
    kapt(Libs.Hilt.androidCompiler)
    implementation(Libs.Hilt.viewModel)
    kapt(Libs.Hilt.hiltCompiler)
}