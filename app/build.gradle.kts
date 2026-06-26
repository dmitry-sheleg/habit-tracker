plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "ru.netology.habittracker"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "ru.netology.habittracker"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
//    implementation(libs.androidx.compose.material3)
//    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.remote.creation.core)
//    implementation(libs.androidx.compose.ui)
//    implementation(libs.androidx.compose.ui.graphics)
//    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.room3.common.jvm)
    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.material3)
//    implementation(libs.androidx.navigation.compose)
//    implementation(libs.androidx.compose.material)
    testImplementation(libs.junit)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
//    debugImplementation(libs.androidx.compose.ui.test.manifest)
//    debugImplementation(libs.androidx.compose.ui.tooling)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // UI
    implementation("androidx.compose.ui:ui:1.6.2")
    implementation("androidx.compose.ui:ui-graphics:1.6.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.2")

    // Material 3 (стабильные компоненты)
    implementation("androidx.compose.material3:material3:1.2.1")

    // Material (нужен для SwipeToDismiss, DismissState и т.д.)
    implementation("androidx.compose.material:material:1.7.4")

    // Иконки
    implementation("androidx.compose.material:material-icons-core:1.7.4")

    // Остальное
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.navigation:navigation-compose:2.8.9")

    debugImplementation("androidx.compose.ui:ui-tooling:1.6.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.2")
}