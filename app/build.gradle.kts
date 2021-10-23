plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
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
        versionName =
            "${Versions.App.major}.${Versions.App.feature}.${Versions.App.regular}.${Versions.App.minor}"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../seeHyangKeystore")
            storePassword = "seeHyang1234"
            keyAlias = "seeHyangKey"
            keyPassword = "seeHyang1234"
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
    implementation(project(":base"))

    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Hilt
    implementation(Libs.Hilt.hilt)
    kapt(Libs.Hilt.androidCompiler)
    implementation(Libs.Hilt.viewModel)
    kapt(Libs.Hilt.hiltCompiler)

    // Navigation
    implementation(Libs.AndroidX.Navigation.fragment)
    implementation(Libs.AndroidX.Navigation.ui)

    // Test
    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.espressoCore)

    // Crop
    implementation(Libs.ThirdParty.crop)

    // Firebase
    implementation(platform(Libs.Firebase.bom))
    implementation(Libs.Firebase.analytics)

    // Editor
    implementation(Libs.ThirdParty.editor)

    // Room
    implementation(Libs.AndroidX.Room.room)
    kapt(Libs.AndroidX.Room.compiler)
    implementation(Libs.AndroidX.Room.ktx)
}