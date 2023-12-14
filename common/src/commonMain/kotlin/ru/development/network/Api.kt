package ru.development.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import ru.development.Serialization
import ru.development.models.*

class NotFoundError(msg: String) : Throwable(msg)
class UnknownError(msg: String) : Throwable(msg)


object Api {

    suspend fun login(login: String, password: String): Result<User> = runCatching {
        val response = client.post("http://localhost:8080/api/login") {
            setBody(mapOf("login" to login, "password" to password))
        }

        when (response.status) {
            HttpStatusCode.OK -> response.body()
            HttpStatusCode.NotFound -> throw NotFoundError("Пользователь не найден")
            else -> throw UnknownError(msg = "Не удалось выполнить авторизацию")
        }
    }

    suspend fun changePassword(userId: Int, oldPassword: String, newPassword: String): Result<Unit> = runCatching {
        val response = client.post("http://localhost:8080/api/changePassword") {
            setBody(UserIdWithNewPassword(userId, oldPassword, newPassword))
        }

        when (response.status) {
            HttpStatusCode.OK -> response.body()
            HttpStatusCode.NotFound -> throw NotFoundError("Кажется введенн неверный старый пароль")
            else -> throw UnknownError(msg = "Не удалось сменить пароль")
        }
    }

    suspend fun getBooks(): Result<List<BookInfo>> = runCatching {
        val response = client.get("http://localhost:8080/api/books")

        if (response.status == HttpStatusCode.OK) response.body()
        else throw UnknownError(msg = "Не удалось получить список книг")
    }

    suspend fun getNews(): Result<List<News>> = runCatching {
        val response = client.get("http://0.0.0.0:8080/api/news")

        if (response.status == HttpStatusCode.OK) response.body()
        else throw UnknownError(msg = "Не удалось получить список новостей")
    }

    suspend fun addNews(news: News): Result<Unit> = runCatching {
        val response = client.post("http://0.0.0.0:8080/api/addNews") {
            setBody(news)
        }

        if (response.status == HttpStatusCode.OK) response.body()
        else throw UnknownError(msg = "Не удалось сохранить новость")
    }

    suspend fun getBookOrderList(userId: Int): Result<List<BookOrder>> = runCatching {
        val response = client.get("http://localhost:8080/api/bookOrder/$userId")

        if (response.status == HttpStatusCode.OK) response.body()
        else throw UnknownError(msg = "Не удалось получить список выданных книг")
    }

    suspend fun getReadersWithCredential(): Result<List<RegisterCredential>> = runCatching {
        val response = client.get("http://localhost:8080/api/readersWithCredential")

        if (response.status == HttpStatusCode.OK) response.body()
        else throw UnknownError(msg = "Ошибка получения списка читателей")
    }

    suspend fun getReaders(): Result<List<User>> = runCatching {
        val response = client.get("http://localhost:8080/api/readers")

        if (response.status == HttpStatusCode.OK) response.body()
        else throw UnknownError(msg = "Ошибка получения списка читателей")
    }

    suspend fun addBook(book: BookInfo): Result<Unit> = runCatching {
        val response = client.post("http://localhost:8080/api/addBook") {
            setBody(book)
        }

        if (response.status == HttpStatusCode.OK) Unit
        else throw UnknownError(msg = "Ошибка добавления книги")
    }

    suspend fun register(credential: RegisterCredential): Result<Unit> = runCatching {
        val response = client.post("http://localhost:8080/api/register") {
            setBody(credential)
        }

        if (response.status == HttpStatusCode.Created) Unit
        else throw UnknownError(msg = "Ошибка регистрации читателя")
    }

    suspend fun deleteBook(id: Int): Result<Unit> = runCatching {
        val response = client.delete("http://localhost:8080/api/book/$id")

        if (response.status == HttpStatusCode.OK) Unit
        else throw UnknownError(msg = "Ошибка удаления книги")
    }

    suspend fun deleteUser(id: Int): Result<Unit> = runCatching {
        val response = client.delete("http://localhost:8080/api/user/$id")

        if (response.status == HttpStatusCode.OK) Unit
        else throw UnknownError(msg = "Ошибка удаления читателя")
    }

    suspend fun deleteNews(id: Int): Result<Unit> = runCatching {
        val response = client.delete("http://0.0.0.0:8080/api/news/$id")

        if (response.status == HttpStatusCode.OK) Unit
        else throw UnknownError(msg = "Ошибка удаления новости")
    }

    suspend fun createOrder(userOrder: UserIdToBookIdWithOrderRef) = runCatching {
        val response = client.post("http://localhost:8080/api/createOrder") {
            setBody(userOrder)
        }

        if (response.status == HttpStatusCode.Created) Unit
        else throw UnknownError(msg = "Ошибка выдачи книги")
    }

    suspend fun removeOrder(orderId: Int): Result<Unit> = runCatching {
        val response = client.delete("http://localhost:8080/api/removeOrder/$orderId")

        if (response.status == HttpStatusCode.OK) Unit
        else throw UnknownError(msg = "Ошибка сдачи книги")
    }

    suspend fun backupDatabase(id: Int): Result<Unit> = runCatching {
        val response = client.post("http://0.0.0.0:8080/api/backupDatabase")

        if (response.status == HttpStatusCode.OK) response.body()
        else throw UnknownError(msg = "Ошибка создания бэкапа :(")
    }


    private val engine = createEngine()

    private val client = HttpClient(engine) {
        install(ContentNegotiation) { json(Serialization.json) }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            url {
                protocol = URLProtocol.HTTP
            }
        }
    }
}