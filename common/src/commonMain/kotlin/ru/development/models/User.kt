package ru.development.models

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Serializable(with = ModuleSerializer::class)
sealed class UserType {
    abstract val typeName: String
}

// Просматривает взятые книги
@Serializable
data class Reader(
    val info: PeopleInfo,
    var ticketNumber: Int = -1,
    val limitOfBooks: Int,
    val passportSerialNumber: String,
    override val typeName: String,
) : UserType() {
    constructor(
        info: PeopleInfo,
        ticketNumber: Int,
        limitOfBooks: Int,
        passportSerialNumber: String
    ) : this(info, ticketNumber, limitOfBooks, passportSerialNumber, "Reader")

    companion object {
        fun filterCondition(type: Reader, query: String): Boolean {
            return type.toString().lowercase().contains(query.lowercase())
        }
    }
}

// Регистрирует читателя, выдает книги
@Serializable
class LibraryWorker(
    val info: PeopleInfo,
    override val typeName: String
) : UserType()

// Редактирует книги
@Serializable
class BookRegistrar(
    val info: PeopleInfo,
    override val typeName: String
) : UserType()

@Serializable
data class OrderInfo(var id: Int = -1, val dateStartInS: Long, val dateEndInS: Long) {
    val displayDateStart: String
        get() {
            val instant = Instant.fromEpochSeconds(dateStartInS).toLocalDateTime(TimeZone.currentSystemDefault())
            return "${instant.year}.${instant.monthNumber}.${instant.dayOfMonth}"
        }

    val displayDateEnd: String
        get() {
            val instant = Instant.fromEpochSeconds(dateEndInS).toLocalDateTime(TimeZone.currentSystemDefault())
            return "${instant.year}.${instant.monthNumber}.${instant.dayOfMonth}"
        }

    val displayFullTime: String = "$displayDateStart - $displayDateEnd"
}

@Serializable
data class BookIdWithOrder(val bookId: Int, val info: OrderInfo)

@Serializable
data class BookOrder(val book: BookInfo, val info: OrderInfo) {
    companion object {
        fun filterCondition(query: String, bookOrder: BookOrder): Boolean {
            return bookOrder.toString().lowercase().contains(query.lowercase())
        }


        fun List<BookOrder>.sortedByFiledIndex(index: Int?): List<BookOrder> {
            if (index == null) return this

           return sortedBy { order ->
               when (index)  {
                   0 -> order.book.name
                   1 -> order.book.author
                   2 -> order.book.year
                   3 -> order.book.description
                   4 -> order.info.displayDateStart
                   5 -> order.info.displayDateEnd
                   else -> throw RuntimeException("Неизвестный индекс соритровки")
               }
           }
        }
    }
}

@Serializable
data class UserIdToBookIdWithOrderRef(val userId: Int, val order: BookIdWithOrder)

@Serializable
data class User(var id: Int = -1, val type: UserType)

@Serializable
data class PeopleInfo(
    val secondName: String,
    val firstName: String,
    val fatherName: String,
    val address: String,
    val phoneNumber: String,
    val birthday: Birthday
) {
    val fullname: String
        get() = "$secondName $firstName $fatherName"
}

@Serializable
data class Birthday(val year: Int, val month: Int, val day: Int) {
    override fun toString(): String {
        return "$year $month $day"
    }
}

object ModuleSerializer : JsonContentPolymorphicSerializer<UserType>(UserType::class) {
    private val key = "typeName"
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<UserType> {
        return when (element.jsonObject[key]?.jsonPrimitive?.content) {
            "Reader" -> Reader.serializer()
            "LibraryWorker" -> LibraryWorker.serializer()
            "BookRegistrar" -> BookRegistrar.serializer()
            else -> throw Exception("Unknown Module: key '$key' not found or does not matches any module type: $element")
        }
    }
}