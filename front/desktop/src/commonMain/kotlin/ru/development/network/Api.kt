package ru.development.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import ru.development.Serialization
import ru.development.models.*

class NotFoundError: Throwable()

object Api {

    suspend fun login(login: String, password: String): Result<User> = runCatching {
        val response = client.post("http://0.0.0.0:8080/api/login") {
            setBody(mapOf("login" to login, "password" to password))
        }

        if (response.status == HttpStatusCode.OK) response.body()
        else if (response.status == HttpStatusCode.NotFound) throw NotFoundError()
        else throw UnknownError()
    }

    suspend fun getBooks(): Result<List<BookInfo>> = runCatching {
        val response = client.get("http://0.0.0.0:8080/api/books")

        if (response.status == HttpStatusCode.OK) response.body()
        else throw UnknownError()
    }

//    suspend fun getReaders(): Result<List<User>> = runCatching {
//        val response = client.get("http://0.0.0.0:8080/api/readers")
//
//        if (response.status == HttpStatusCode.OK) response.body()
//        else throw UnknownError()
//    }

    suspend fun getBookOrderList(userId: Int): Result<List<BookOrder>> = runCatching {
        val response = client.get("http://0.0.0.0:8080/api/bookOrder/$userId")

        if (response.status == HttpStatusCode.OK) response.body()
        else throw UnknownError()
    }

    suspend fun getReadersWithCredential(): Result<List<RegisterCredential>> = runCatching {
        val response = client.get("http://0.0.0.0:8080/api/readersWithCredential")

        if (response.status == HttpStatusCode.OK) response.body()
        else throw UnknownError()
    }

    suspend fun getReaders(): Result<List<User>> = runCatching {
        val response = client.get("http://0.0.0.0:8080/api/readers")

        if (response.status == HttpStatusCode.OK) response.body()
        else throw UnknownError()
    }

    suspend fun addBook(book: BookInfo): Result<Unit> = runCatching {
        val response = client.post("http://0.0.0.0:8080/api/addBook") {
            setBody(book)
        }

        if (response.status == HttpStatusCode.OK) Unit
        else throw UnknownError()
    }

    suspend fun register(credential: RegisterCredential): Result<Unit> = runCatching {
        val response = client.post("http://0.0.0.0:8080/api/register") {
            setBody(credential)
        }

        if (response.status == HttpStatusCode.Created) Unit
        else throw UnknownError()
    }

    suspend fun deleteBook(id: Int): Result<Unit> = runCatching {
        val response = client.delete("http://0.0.0.0:8080/api/book/$id")

        if (response.status == HttpStatusCode.OK) Unit
        else throw UnknownError()
    }

    suspend fun deleteUser(id: Int): Result<Unit> = runCatching {
        val response = client.delete("http://0.0.0.0:8080/api/user/$id")

        if (response.status == HttpStatusCode.OK) Unit
        else throw UnknownError()
    }

    suspend fun createOrder(userOrder: UserIdToBookIdWithOrderRef) = runCatching {
        val response = client.post("http://0.0.0.0:8080/api/createOrder") {
            setBody(userOrder)
        }

        if (response.status == HttpStatusCode.Created) Unit
        else throw UnknownError()
    }

    suspend fun removeOrder(orderId: Int): Result<Unit> = runCatching {
        val response = client.delete("http://0.0.0.0:8080/api/removeOrder/$orderId")

        if (response.status == HttpStatusCode.OK) Unit
        else throw UnknownError()
    }

    val client = HttpClient(Java) {
        install(ContentNegotiation) { json(Serialization.json) }
        defaultRequest {
            contentType(ContentType.Application.Json)
            url {
                protocol = URLProtocol.HTTP
//                host = "http://0.0.0.0:8080"
            }
        }
    }
}