plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.apkcontrol_asistencias"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.apkcontrol_asistencias"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude ("META-INF/INDEX.LIST")

    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.zxing:core:3.4.1")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.journeyapps:zxing-android-embedded:4.2.0")
    implementation("com.loopj.android:android-async-http:1.4.11")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.google.android.gms:play-services-vision:20.1.3")
    implementation ("com.google.cloud:google-cloud-vision:3.37.0") // Verifica la versión más reciente
    implementation ("com.google.api-client:google-api-client-android:2.4.0")
    implementation ("com.google.apis:google-api-services-vision:v1-rev20210423-1.31.5")
    implementation ("com.google.http-client:google-http-client-android:1.44.1")
    implementation ("com.convertapi.client:convertapi:2.10")
}