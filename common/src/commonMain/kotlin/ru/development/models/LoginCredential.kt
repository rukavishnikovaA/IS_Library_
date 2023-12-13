package ru.development.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginCredential(val login: String, val password: String)

@Serializable
data class RegisterCredential(
    val login: LoginCredential,
    val user: User
) {
    companion object {
        fun List<RegisterCredential>.sortByFiledIndex(index: Int?): List<RegisterCredential> {
            if (index == null) return this

            return when (index) {
                0 -> sortedBy { (it.user.type as Reader).ticketNumber }
                8 -> sortedBy { (it.user.type as Reader).limitOfBooks }
                else -> sortedBy { credential ->
                    val user = credential.user
                    val type = user.type as Reader
                    val info = type.info

                    when (index) {
                        1 -> info.secondName
                        2 -> info.firstName
                        3 -> info.fatherName
                        4 -> info.address
                        5 -> info.phoneNumber
                        6 -> type.passportSerialNumber
                        7 -> info.birthday.toString()
                        else -> {
                            throw RuntimeException("Неизвестный индекс '$index' для сортировки по полям!!")
                        }
                    }
                }
            }
        }
    }
}