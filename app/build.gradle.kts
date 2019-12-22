import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.2")
    defaultConfig {
        applicationId = "com.sxhardha.someappName"
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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

    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}

dependencies {
    val fragmentVersion = "1.2.0-rc03"
    val retrofitVersion = "2.7.0"
    val lifecycleVersion = "2.2.0-rc03"

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${rootProject.extra.get("kotlinVersion")}")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.1.0")
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.lifecycle:lifecycle-extensions:2.1.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.google.android.material:material:1.2.0-alpha02")
    implementation("androidx.fragment:fragment:$fragmentVersion")
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.dagger:dagger:2.25.3")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.moshi:moshi-kotlin:1.9.2")
    implementation("com.squareup.moshi:moshi:1.9.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.2.2")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    compileOnly("com.squareup.inject:assisted-inject-annotations-dagger2:0.5.2")
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("com.jakewharton.timber:timber:4.7.1")
    implementation("com.github.coroutineDispatcher:Rocket:1.3.1")
    //processors
    kapt("com.google.dagger:dagger-compiler:2.25.3")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.9.2")
    kapt("com.squareup.inject:assisted-inject-processor-dagger2:0.5.2")
    //tests
    testImplementation("junit:junit:4.12")
    //android tests
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
