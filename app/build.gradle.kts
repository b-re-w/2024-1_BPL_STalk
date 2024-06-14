import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.chaquo.python)
    id("com.google.devtools.ksp").version("2.0.0-1.0.22")
}

group = "io.github.brew.bpl.stalk"
version = "1.0.0"

chaquopy {
    defaultConfig {
        version = "3.11"
    }
    sourceSets {
        getByName("main") {
            srcDirs("src/androidMain/python", "src/commonMain/python")
        }
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "App"
            isStatic = true
        }
    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val isArm64 = System.getProperty("os.arch") == "aarch64"
    val nativeTarget = when {
        hostOs == "Mac OS X" && !isArm64 -> macosX64("pythonMain")
        hostOs == "Linux" && !isArm64 -> linuxX64("pythonMain")
        hostOs == "Mac OS X" && isArm64 -> macosArm64("pythonMain")
        hostOs == "Linux" && isArm64 -> linuxArm64("pythonMain")
        isMingwX64 -> mingwX64("pythonMain")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }
    
    sourceSets {
        val pyMain by getting {
            dependencies {
                implementation(project(":kpy-library"))
            }

            kotlin.srcDir(layout.buildDirectory.dir("generated/ksp/py/pythonMain/kotlin"))
        }

        commonMain.dependencies {
            //api(projects.pycomposeui)
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        
        androidMain.dependencies {
            api(compose.preview)
            api(libs.androidx.activity.compose)
        }

        val desktopMain by getting
        desktopMain.dependencies {
            api(compose.preview)
            api(compose.desktop.currentOs)
        }
        desktopMain.dependsOn(pyMain)
    }
}

android {
    namespace = "$group.android"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "$group.android"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = version.toString()

        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86_64", "x86")
        }
    }
    sourceSets {
        getByName("main") {
            kotlin.srcDirs("src/androidMain/kotlin")
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
            resources.srcDirs("src/commonMain/resources")
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "Main_DesktopKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "$group.desktop"
            packageVersion = version.toString()
        }
    }
}
