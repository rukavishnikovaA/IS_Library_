plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":common"))

                implementation(compose.runtime)
                implementation(compose.html.core)
                implementation(compose.html.svg)

                implementation("org.jetbrains.kotlin-wrappers:kotlin-extensions:${properties["kotlin.wrappers.extensions.version"]}")
                implementation(kotlin("stdlib-js"))
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}