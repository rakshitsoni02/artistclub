// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(Dependencies.gradle)
        classpath(Dependencies.kotlinGradle)
        classpath(Dependencies.daggerHilt)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter() // Warning: this repository is going to shut down soon
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}