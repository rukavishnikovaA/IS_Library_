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

        fun List<BookInfo>.sortedByFiledIndex(descending: Boolean, index: Int?): List<BookInfo> {
            if (index == null) return this

            val sortedLambda: (BookInfo) -> String = { book ->
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

            return when (index) {
                7 -> if (descending) sortedByDescending { it.count } else sortedBy { it.count }
                8 -> if (descending) sortedByDescending { it.pageCount } else sortedBy { it.pageCount }
                else -> return if (descending) sortedByDescending(sortedLambda) else sortedBy(sortedLambda)
            }
        }

    }
}


@Serializable
data class BookIdWithOrder(val bookId: Int, val info: OrderInfo)

@Serializable
data class BookOrder(val book: BookInfo, val info: OrderInfo) {
    companion object {
        fun filterCondition(query: String, bookOrder: BookOrder): Boolean {
            return bookOrder.toString().lowercase().contains(query.lowercase())
        }


        fun List<BookOrder>.sortedByFiledIndex(descending: Boolean, index: Int?): List<BookOrder> {
            if (index == null) return this

            val sortLambda: (BookOrder) -> String = { order ->
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

            return if (descending) sortedByDescending(sortLambda) else sortedBy(sortLambda)
        }
    }
}
