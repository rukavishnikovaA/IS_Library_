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

@Serializable
data class SysAdmin(override val typeName: String) : UserType() {
    constructor() : this("SysAdmin")
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

@Serializable
data class UserToCredentialRef(val userId: Int, val credential: LoginCredential)

@Serializable
data class UserIdWithNewPassword(val userId: Int, val oldPassword: String, val newPassword: String)


object ModuleSerializer : JsonContentPolymorphicSerializer<UserType>(UserType::class) {
    private val key = "typeName"
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<UserType> {
        return when (element.jsonObject[key]?.jsonPrimitive?.content) {
            "Reader" -> Reader.serializer()
            "LibraryWorker" -> LibraryWorker.serializer()
            "BookRegistrar" -> BookRegistrar.serializer()
            "SysAdmin" -> SysAdmin.serializer()
            else -> throw Exception("Unknown Module: key '$key' not found or does not matches any module type: $element")
        }
    }
}