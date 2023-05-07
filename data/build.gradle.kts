@file:Suppress("UnstableApiUsage")

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.detekt)
    alias(libs.plugins.junit)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)
}

android {
    compileSdk = 33
    namespace = "com.bishal.data"

    with (defaultConfig) {
        minSdk = 25
        targetSdk = 33
    }


    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            consumerProguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    kotlinOptions {
        freeCompilerArgs = listOf(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        )
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    sourceSets {
        getByName("androidTest") {
            java.srcDir(project(":data").file("src/androidTest/java"))
        }
        getByName("test") {
            java.srcDir(project(":data").file("src/test/java"))
        }
    }
}

dependencies {

    //optimise dependency based on usage just to run i have copied all dependency
    implementation(project(":domain"))

    implementation(libs.hilt)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.serialization)
    implementation(libs.retrofit)
    implementation(libs.room)
    implementation(libs.composeruntime)
    implementation(libs.timber)
    implementation(libs.loggingInterceptor)
    implementation(libs.gsonconvertor)
    testImplementation(libs.bundles.common.test)
    androidTestImplementation(libs.bundles.common.android.test)
    kapt(libs.hilt.compiler)
    kaptAndroidTest(libs.test.android.hilt.compiler)
    coreLibraryDesugaring(libs.desugar)
    detektPlugins(libs.detekt.compose.rules)
}
