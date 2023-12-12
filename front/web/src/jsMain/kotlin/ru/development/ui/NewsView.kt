package ru.development.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import ru.development.MainStyle
import ru.development.models.News

@Composable
fun NewsView() {
    val list = listOf(News.default, News.default, News.default)

    Div(attrs = {
        classes(MainStyle.column, MainStyle.newsList)
    }) {
        list.forEach { news: News ->
            NewsItemView(news)
        }
    }
}

@Composable
fun NewsItemView(news: News) {
    Div(attrs = {
        classes(MainStyle.column, MainStyle.newsItem)
    }) {
        Span(attrs = {
            style {
                color(Color.black)
                fontSize(36.px)
                fontWeight(800)
                marginBottom(40.px)
            }
        }) { Text(news.title) }

        Span(attrs = {
            style {
                color(Color.black)
                fontSize(24.px)
                fontWeight(800)
                marginBottom(40.px)
            }
        }) { Text(news.title2) }

        Img(src = news.imgSrc) {
            style {
                property("object-fit", "cover")
                marginBottom(30.px)
            }
        }

        Span(attrs = {
            style {
                color(Color.black)
                fontSize(24.px)
                fontWeight(800)
                marginBottom(40.px)
            }
        }) { Text(news.text) }
    }
}