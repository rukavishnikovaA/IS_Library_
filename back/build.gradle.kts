plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("io.ktor.plugin") version "2.3.6"
}

application {
    mainClass.set("ru.development.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    val logback_version = "1.4.11"

    implementation(project(":common"))

    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")

    implementation("io.ktor:ktor-server-html-builder-jvm")
    implementation("org.jetbrains:kotlin-css-jvm:1.0.0-pre.129-kotlin-1.4.20")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // https://mvnrepository.com/artifact/io.ktor/ktor-server-resources-jvm
    implementation("io.ktor:ktor-server-resources-jvm:2.3.0")
}

