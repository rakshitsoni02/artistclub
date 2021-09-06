plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(AppConfig.COMPILE_SDK)
    buildToolsVersion(AppConfig.BUILD_TOOL_VERSION)

    defaultConfig {
        applicationId = "com.dice.app"
        minSdkVersion(AppConfig.MIN_SDK)
        targetSdkVersion(AppConfig.TARGET_SDK)
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.viewBinding = true

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

    packagingOptions {
        exclude("META-INF/*")
    }
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(project(":shared"))
    api(project(":feature-artist-overview"))

    //Kotlin
    implementation(Dependencies.kotlin)

    //Support
    implementation(Android.coreKtx)
    implementation(Android.fragmentKtx)
    implementation(Android.appcompat)
    implementation(Android.recyclerViewSelection)
    implementation(Dependencies.materialDesign)

    //Dagger hilt
    implementation(Hilt.hiltAndroid)
    kapt(Hilt.daggerCompiler)
    implementation(Hilt.hiltViewModel)
    kapt(Hilt.hiltCompiler)

    //Networking
    implementation(Retrofit.retrofit)
    implementation(Retrofit.gsonRetrofitConverter)

    // Testing
    testImplementation(Testing.jUnit)
    testImplementation(Testing.mockitokotlin)

    // Android Testing
    androidTestImplementation(Testing.extJUnit)
    androidTestImplementation(Testing.espresso)
    androidTestImplementation(Testing.mockitokotlin)
}