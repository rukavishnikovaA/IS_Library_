package ru.development

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.development.plugins.configureRest
import ru.development.plugins.configureRouting
import ru.development.plugins.configureSerialization
import ru.development.plugins.configureTemplating

fun main() {
    Repository()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRest()
    configureTemplating()
    configureRouting()
}
