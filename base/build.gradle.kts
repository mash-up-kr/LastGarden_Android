plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdk = 23
        targetSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        viewBinding {
            isEnabled = true
        }

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Kotlin
    api(Libs.Kotlin.stdlib)

    // Android
    api(Libs.AndroidX.appcompat)
    api(Libs.AndroidX.constraintLayout)
    api(Libs.AndroidX.recyclerView)

    // Android KTX
    api(Libs.AndroidX.coreKtx)
    api(Libs.AndroidX.fragmentKtx)
    api(Libs.AndroidX.collectionKtx)

    // Android Architecture Components
    api(Libs.AndroidX.Lifecycle.viewModel)
    api(Libs.AndroidX.Lifecycle.liveData)
    api(Libs.AndroidX.Lifecycle.reactiveStream)
    api(Libs.AndroidX.Lifecycle.common)

    // Material
    api(Libs.material)

    // OkHttp
    api(platform(Libs.Network.OkHttp.bom))
    api(Libs.Network.OkHttp.okhttp)
    api(Libs.Network.OkHttp.logging)

    // Retrofit
    api(Libs.Network.Retrofit.retrofit)
    api(Libs.Network.Retrofit.gsonConverter)

    // Retrofit - Coroutine network response adapter
    //api "com.github.haroldadmin:NetworkResponseAdapter:4.1.0"

    // Glide
    api(Libs.Glide.glide)
    kapt(Libs.Glide.compiler)
    api(Libs.Glide.integration)
    api(Libs.Glide.transformation)

    // Room
    api(Libs.AndroidX.Room.room)
    kapt(Libs.AndroidX.Room.compiler)

    // An adaptation of the JSR-310 backport for Android.
    api("com.jakewharton.threetenabp:threetenabp:1.2.1")
    testImplementation("org.threeten:threetenbp:1.4.0")

    // Test
    testApi(Libs.Test.junit)
    androidTestApi(Libs.AndroidX.Test.junit)
    androidTestApi(Libs.AndroidX.Test.espressoCore)
}