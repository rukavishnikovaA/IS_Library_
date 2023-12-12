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
        val default = BookInfo(
            name = "Name",
            author = "Book",
            factory = "Factory",
            description = "Description",
            year = "Year",
            janre = "Description",
            language = "Lang",
            count = Random.nextInt(),
            pageCount = Random.nextInt(),
            downloadAddress = "some"
        )
    }
}