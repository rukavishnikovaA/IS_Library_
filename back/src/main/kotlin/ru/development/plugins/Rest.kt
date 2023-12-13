package ru.development.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.development.models.*
import ru.development.util.Database


fun Application.configureRest() {

    routing {
        post("/api/login") {
            val credential = call.receive<LoginCredential>()
            val user = Database.findUser(credential)

            if (user == null) call.respond(HttpStatusCode.NotFound, "404")
            else call.respond(HttpStatusCode.OK, user)
        }

        // DEBUG
        get("/debug/users") {
            val users = Database.getUsers()

            call.respond(HttpStatusCode.OK, users)
        }

        get("/api/books") {
            val books = Database.getBookList()

            call.respond(HttpStatusCode.OK, books)
        }

        get("/api/readers") {
            val readers = Database.getUsers().filter { it.type is Reader }

            call.respond(HttpStatusCode.OK, readers)
        }

        get("/api/readersWithCredential") {
            val res = Database.getRegistrationCredentialList()
            val readers = res.filter { it.user.type is Reader }

            call.respond(HttpStatusCode.OK, readers)
        }

        get("/api/bookOrder/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")

            val res = Database.getBookOrderList(id)
            call.respond(HttpStatusCode.OK, res)
        }

        post("/api/changePassword") {
            val userIdWithNewPassword = call.receive<UserIdWithNewPassword>()

            val res = Database.changePassword(userIdWithNewPassword.userId, userIdWithNewPassword.oldPassword, userIdWithNewPassword.newPassword)
            if (res == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(HttpStatusCode.OK)
        }

        // Create user
        post("/api/register") {
            val registerCredential = call.receive<RegisterCredential>()
            Database.registerUser(registerCredential)

            call.respond(HttpStatusCode.Created, registerCredential)
        }

        post("/api/addBook") {
            val book = call.receive<BookInfo>()
            Database.addOrEditBook(book)

            call.respond(HttpStatusCode.OK, book)
        }

        post("/api/createOrder") {
            val userOrder = call.receive<UserIdToBookIdWithOrderRef>()

            Database.createOrder(userOrder)

            call.respond(HttpStatusCode.Created)
        }

        delete("/api/book/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            Database.deleteBook(id)

            call.respond(HttpStatusCode.OK)
        }

        delete("/api/user/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            Database.deleteUser(id)

            call.respond(HttpStatusCode.OK)
        }

        delete("/api/removeOrder/{id}") {
            val orderId = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            Database.removeOrder(orderId)

            call.respond(HttpStatusCode.OK)
        }
    }
}
