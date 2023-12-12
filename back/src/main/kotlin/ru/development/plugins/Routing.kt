package ru.development.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        get("/") {
            val file = File("./files/test.txt")
            call.respondText("Hello World! + ${file.absolutePath}")
        }
    }
}
