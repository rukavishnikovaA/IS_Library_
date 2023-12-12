package ru.development.util

import kotlinx.serialization.encodeToString
import ru.development.Serialization
import ru.development.models.*
import java.io.File
import java.io.FileWriter
import kotlin.random.Random

object FileUtil {
    fun getSelfLocation(): String {
        return File(FileUtil::class.java.getProtectionDomain().codeSource.location.toURI()).path
    }

    fun getResourcesFile(resource: String): File? {
        val url = FileUtil::class.java.getResource("/resources/$resource") ?: FileUtil::class.java.getClassLoader()
            .getResource(resource) ?: return null

        return File(url.file)
    }

    fun getOrCreateGlobalFile(path: String): File {
        return File("./$path").also { if (!it.exists()) it.createNewFile() }
    }
}

object Database {
    val credentialToUserRefFile by lazy { FileUtil.getOrCreateGlobalFile("userToCredentialRef.json") }
    val usersFile by lazy { FileUtil.getOrCreateGlobalFile("users.json") }
    val bookListFile by lazy { FileUtil.getOrCreateGlobalFile("books.json") }
    val userOrderListFile by lazy { FileUtil.getOrCreateGlobalFile("orders.json") }

    fun generateId() = Random.nextInt(0, Int.MAX_VALUE)

    fun registerUser(register: RegisterCredential) {
        val type = register.user.type

        val exist = getUsers().filter { it.id != register.user.id }
        val totalUsers = exist + register.user

        if (register.user.id == -1) register.user.id = generateId()
        if (type is Reader) {
            type.ticketNumber = register.user.id
        }

        val readersJson = Serialization.json.encodeToString(totalUsers)

        usersFile.rewrite(readersJson)

        addCredentialToUserRef(UserToCredentialRef(register.user.id, register.login))
    }

    fun getUsers(): List<User> {
        val rawText = usersFile.readText()
        if (rawText.isBlank()) return emptyList()

        return Serialization.json.decodeFromString(rawText)
    }

    fun findUser(credential: LoginCredential): User? {
        val credentialToUserRefList = getCredentialToUserRefList()
        val target = credentialToUserRefList.find {
            it.credential.password == credential.password && it.credential.login == credential.login
        } ?: return null

        return findUser(target.userId)
    }

    fun findUser(id: Int): User? {
        return getUsers().find { it.id == id }
    }

    fun getCredentialToUserRefList(): List<UserToCredentialRef> {
        val rawText = credentialToUserRefFile.readText()
        if (rawText.isBlank()) return emptyList()

        return Serialization.json.decodeFromString(rawText)
    }

    fun addCredentialToUserRef(ref: UserToCredentialRef) {
        val list = getCredentialToUserRefList().filter { it.userId != ref.userId }

        val total = list + ref
        val totalJson = Serialization.json.encodeToString(total)

        credentialToUserRefFile.rewrite(totalJson)
    }

    fun addOrEditBook(book: BookInfo) {
        val list = getBookList().filter { it.id != book.id }

        if (book.id == -1) book.id = generateId()

        val total = list + book
        val totalJson = Serialization.json.encodeToString(total)

        bookListFile.rewrite(totalJson)
    }

    fun getBookList(): List<BookInfo> {
        val rawText = bookListFile.readText()
        if (rawText.isBlank()) return emptyList()

        return Serialization.json.decodeFromString(rawText)
    }

    fun deleteBook(id: Int) {
        val total = getBookList().filter { it.id != id }
        val totalJson = Serialization.json.encodeToString(total)
        bookListFile.rewrite(totalJson)
    }

    fun deleteUser(id: Int) {
        deleteCredentialRef(id)

        val total = getUsers().filter { it.id != id }
        val totalJson = Serialization.json.encodeToString(total)

        usersFile.rewrite(totalJson)
    }

    fun deleteCredentialRef(userId: Int) {
        val total = getCredentialToUserRefList().filter { it.userId != userId }

        val totalJson = Serialization.json.encodeToString(total)

        credentialToUserRefFile.rewrite(totalJson)
    }

    fun getRegistrationCredentialList(): List<RegisterCredential> {
        val credentialToUserRef = getCredentialToUserRefList()
        return credentialToUserRef.mapNotNull { ref ->
            val user = findUser(ref.userId)
            if (user != null) RegisterCredential(ref.credential, user)
            else null
        }
    }

    fun getBook(bookId: Int): BookInfo? {
        return getBookList().find { it.id == bookId }
    }

    fun getBookOrderList(userId: Int): List<BookOrder> {
        val refs = getUserOrdersRefs().filter { it.userId == userId }

        return refs.mapNotNull { ref ->
            val target = getBook(ref.order.bookId)
            if (target != null) BookOrder(target, ref.order.info)
            else null
        }
    }

    fun getUserOrdersRefs(): List<UserIdToBookIdWithOrderRef> {
        val raw = userOrderListFile.readText()
        return if (raw.isBlank()) emptyList()
        else Serialization.json.decodeFromString(raw)
    }

    fun createOrder(userIdToBookIdWithOrderRef: UserIdToBookIdWithOrderRef) {
        val list = getUserOrdersRefs().filter { it.order.info.id != userIdToBookIdWithOrderRef.order.info.id }
        val total = list + userIdToBookIdWithOrderRef

        if (userIdToBookIdWithOrderRef.order.info.id == -1) userIdToBookIdWithOrderRef.order.info.id = generateId()

        val totalJson = Serialization.json.encodeToString(total)

        userOrderListFile.rewrite(totalJson)

        val book = getBook(userIdToBookIdWithOrderRef.order.bookId)
        if (book != null) {
            val new = book.copy(count = book.count - 1)
            addOrEditBook(new)
        }
    }

    fun removeOrder(orderId: Int) {
        val fullOrders = getUserOrdersRefs()

        val target = fullOrders.find { it.order.info.id == orderId }
        if (target != null) {

            val newOrders = fullOrders - target
            val totalJson = Serialization.json.encodeToString(newOrders)
            userOrderListFile.rewrite(totalJson)

            val targetBook = getBook(target.order.bookId)
            if (targetBook != null) {
                val newBook = targetBook.copy(count = targetBook.count + 1)
                addOrEditBook(newBook)
            } else throw RuntimeException("Выданная книга не найдена: orderId=$orderId")
        }
    }

    private fun File.rewrite(data: String) {
        val writer = FileWriter(this)
        writer.write(data)
        writer.close()
    }
}