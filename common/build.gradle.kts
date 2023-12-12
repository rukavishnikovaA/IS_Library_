plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.plugin.serialization")
}

version = "1.0"

kotlin {
    jvm()
    js(IR) {
        binaries.executable()
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${properties["kotlinx.serialization.version"]}")
            }
        }
    }
}