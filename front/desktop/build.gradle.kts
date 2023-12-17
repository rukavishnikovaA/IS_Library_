import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":common"))

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.desktop.currentOs)

                implementation("ca.gosyer:compose-material-dialogs-datetime:0.9.4")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "ru.development.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ISLibrary"
            packageVersion = "1.0.0"
            modules("java.net.http")

            macOS {
                iconFile.set(project.file("icon.icns"))
            }
            windows {
                iconFile.set(project.file("icon.ico"))
            }
            linux {
                iconFile.set(project.file("icon.png"))
            }
        }

        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
        }
    }
}
