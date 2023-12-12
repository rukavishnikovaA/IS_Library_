package ru.development.models

import kotlinx.serialization.Serializable

@Serializable
data class MyBook(
    val bookInfo: BookInfo,
    val dateOfEndInS: Long,
    val dateOfStartInS: Long
) {
    companion object {
        val default = MyBook(BookInfo.default, 2000, 3000)
    }
}