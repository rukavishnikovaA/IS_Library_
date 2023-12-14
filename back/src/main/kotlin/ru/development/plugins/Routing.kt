package ru.development.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        staticFiles("/", File("site"))
        staticFiles("/*", File("site"))
        staticResources("/", "site/js/")
        staticResources("/*", "site/js/")
    }
}
