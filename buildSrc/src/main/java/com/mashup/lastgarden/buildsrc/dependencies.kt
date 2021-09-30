object Versions {
    object App {
        const val major = 0
        const val feature = 0
        const val regular = 0
        const val minor = 1
    }
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.2"

    const val junit = "junit:junit:4.13"
    const val material = "com.google.android.material:material:1.4.0"

    object Kotlin {
        private const val version = "1.5.30"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object Hilt {
        private const val version = "2.38.1"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val androidCompiler = "com.google.dagger:hilt-android-compiler:$version"
        const val viewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0"
    }

    object Glide {
        private const val version = "4.11.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
        const val integration = "com.github.bumptech.glide:okhttp3-integration:4.9.0"
        const val transformation = "jp.wasabeef:glide-transformations:4.0.0"
    }

    object Network {
        object Retrofit {
            private const val version = "2.9.0"
            const val retrofit = "com.squareup.retrofit2:retrofit:$version"
            const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
        }

        object OkHttp {
            const val bom = "com.squareup.okhttp3:okhttp-bom:4.9.0"
            const val okhttp = "com.squareup.okhttp3:okhttp"
            const val logging = "com.squareup.okhttp3:logging-interceptor"
        }
    }

    object Test {
        const val junit = "junit:junit:4.12"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.3.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.0"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"

        const val coreKtx = "androidx.core:core-ktx:1.6.0"
        const val collectionKtx = "androidx.collection:collection-ktx:1.1.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.6"

        object Test {
            const val junit = "androidx.test.ext:junit:1.1.3"
            const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
        }

        object Lifecycle {
            private const val version = "2.3.1"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val reactiveStream = "androidx.lifecycle:lifecycle-reactivestreams-ktx:$version"
            const val common = "androidx.lifecycle:lifecycle-common-java8:$version"
        }

        object Navigation {
            private const val version = "2.3.5"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
        }
        
        object Room {
            private const val version = "2.2.6"
            const val room = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }
    }
}