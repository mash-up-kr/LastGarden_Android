import org.jetbrains.kotlin.gradle.plugin.statistics.ReportStatisticsToElasticSearch.url
import java.net.URI

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
    dependencies {
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.Kotlin.gradlePlugin)
        classpath(Libs.Hilt.gradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
    }
}

allprojects {
    repositories {
        google()
        maven { url = URI.create("https://jitpack.io") }
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}