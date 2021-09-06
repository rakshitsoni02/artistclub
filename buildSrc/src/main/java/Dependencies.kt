object Versions {
    const val appcompat = "1.3.1"
    const val fragmentKtx = "1.3.6"
    const val coreKtx = "1.5.0"
    const val recyclerViewSelection = "1.1.0"

    const val kotlinVersion = "1.5.21"
    const val androidGradlePlugin = "4.2.2"
    const val googleMaterial = "1.4.0"
    const val coil = "1.3.0"

    const val coroutines = "1.4.3"
    const val retrofit = "2.9.0"
    const val retrofitLogging = "4.9.1"

    const val hiltAndroid = "2.38"
    const val hiltViewModel = "1.0.0-alpha03"

    const val lifeCycle = "2.2.0"
    const val liveData = "2.3.1"
    const val lifeCycleRuntime = "2.4.0-alpha01"

    const val jUnit = "4.13.2"
    const val androidJUnit = "1.1.3"
    const val espresso = "3.3.0"
    const val mockitoKotlin2 = "2.2.0"
    const val archTesting = "2.1.0"
}

object Android {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val recyclerViewSelection =
        "androidx.recyclerview:recyclerview-selection:${Versions.recyclerViewSelection}"
}

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    const val gradle = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltAndroid}"

    const val materialDesign = "com.google.android.material:material:${Versions.googleMaterial}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
}

object Testing {
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val extJUnit = "androidx.test.ext:junit:${Versions.androidJUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val mockitokotlin =
        "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin2}"
    const val core = "androidx.arch.core:core-testing:${Versions.archTesting}"
}

object Lifecycle {
    const val lifecycleExtensionRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycleRuntime}"
    const val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycle}"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifeCycle}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveData}"
}

object Hilt {
    const val daggerCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroid}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltViewModel}"
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltViewModel}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltAndroid}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.retrofitLogging}"
    const val gsonRetrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
}

object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}