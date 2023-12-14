package ru.development

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import ru.development.plugins.configureRest
import ru.development.plugins.configureRouting
import ru.development.plugins.configureSerialization
import ru.development.plugins.configureTemplating

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.ContentType)
        anyHost()
    }

    configureSerialization()
    configureRest()
    configureTemplating()
    configureRouting()
}
