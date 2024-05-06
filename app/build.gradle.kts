plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.helpme"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.helpme"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    //noinspection KtxExtensionAvailable
    implementation("androidx.navigation:navigation-fragment:2.5.1")
    //noinspection KtxExtensionAvailable
    implementation("androidx.navigation:navigation-ui:2.5.1")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.mlkit:face-detection:16.1.5")
    implementation("com.google.android.gms:play-services-mlkit-face-detection:17.0.1")
    implementation ("androidx.camera:camera-core:1.2.0-alpha04")
    implementation ("androidx.camera:camera-camera2:1.2.0-alpha04")
    implementation ("androidx.camera:camera-lifecycle:1.2.0-alpha04")
    implementation ("androidx.camera:camera-view:1.2.0-alpha04")
    implementation ("org.tensorflow:tensorflow-lite-task-vision:0.3.0")
    implementation ("org.tensorflow:tensorflow-lite-support:0.3.0")
    implementation ("org.tensorflow:tensorflow-lite:0.0.0-nightly-SNAPSHOT")
    implementation("com.google.dagger:hilt-android:2.51")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    annotationProcessor("com.google.dagger:hilt-android-compiler:2.51")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}