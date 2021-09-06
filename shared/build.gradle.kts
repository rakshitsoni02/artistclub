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
    }
}

dependencies {
    //Kotlin
    implementation(Dependencies.kotlin)

    //Support
    implementation(Android.coreKtx)
    implementation(Android.fragmentKtx)
    implementation(Android.appcompat)
    implementation(Dependencies.materialDesign)

    //Dagger hilt
    implementation(Hilt.hiltAndroid)
    kapt(Hilt.daggerCompiler)
    implementation(Hilt.hiltViewModel)
    kapt(Hilt.hiltCompiler)

    //Networking
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okhttpLogging)
    implementation(Retrofit.gsonRetrofitConverter)

    //lifecycle
    implementation(Lifecycle.lifecycleExtensionRuntime)
}