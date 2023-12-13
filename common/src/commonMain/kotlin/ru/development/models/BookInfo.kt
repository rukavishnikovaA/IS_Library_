package ru.development.models

import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class BookInfo(
    var id: Int = -1,
    val name: String,
    val author: String,
    val factory: String,
    val description: String,
    val year: String,
    val janre: String,
    val language: String,
    val count: Int,
    val pageCount: Int,
    val downloadAddress: String?
) {
    val info: String
        get() = "$name $author $year"

    companion object {
        fun filterCondition(book: BookInfo, query: String): Boolean {
            return book.toString().lowercase().contains(query.lowercase())
        }

        fun List<BookInfo>.sortedByFiledIndex(index: Int?): List<BookInfo> {
            if (index == null) return this

            return when (index) {
                7 -> sortedBy { it.count }
                8 -> sortedBy { it.pageCount }
                else -> return sortedBy { book ->
                    when (index) {
                        0 -> book.name
                        1 -> book.author
                        2 -> book.year
                        3 -> book.factory
                        4 -> book.description
                        5 -> book.janre
                        6 -> book.language
                        else -> throw RuntimeException("Неизвестный индекс соритровки")
                    }
                }
            }
        }

    }
}