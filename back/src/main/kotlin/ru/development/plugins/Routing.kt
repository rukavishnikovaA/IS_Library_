package ru.development.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.development.util.Database
import java.io.File

fun Application.configureRouting() {
    routing {
        get("/") {
            val file = Database.usersFile
            call.respondText("Hello World! + ${file.absolutePath}")
        }
    }
}
