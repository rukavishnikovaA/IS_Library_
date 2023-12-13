package ru.development.models

import kotlinx.serialization.Serializable

@Serializable
data class News(val title: String, val title2: String, val imgSrc: String, val text: String) {
    companion object {
        val default = News(
            title = "Новости и мероприятия",
            title2 = "Мы открылись!!!!",
            imgSrc = "https://img.ifunny.co/images/b6c6acde5962a2458062702c24b91aec607fafe096af9bd57a9473f46f74eb94_1.webp",
            text = "Произошло невероятное мы открылись вы не поверите "
        )
    }
}