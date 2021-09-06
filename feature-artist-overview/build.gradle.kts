plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(AppConfig.COMPILE_SDK)

    defaultConfig {
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

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs =
            listOf(*freeCompilerArgs.toTypedArray(),  "-Xno-param-assertions",
                "-Xno-call-assertions",
                "-Xno-receiver-assertions") // compile time visibility lint
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(project(":shared"))
    //Support
    implementation(Android.coreKtx)
    implementation(Android.fragmentKtx)
    implementation(Android.appcompat)
    implementation(Android.recyclerViewSelection)
    implementation(Dependencies.materialDesign)

    //Lifecycle
    implementation(Lifecycle.lifecycleExtension)
    implementation(Lifecycle.lifecycleCommon)
    implementation(Lifecycle.liveData)
    testImplementation(Testing.core)

    //Coroutines
    implementation(Coroutines.core)
    implementation(Coroutines.android)
    implementation(Testing.coroutines)

    //Coil
    implementation(Dependencies.coil)

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