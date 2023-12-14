package ru.development.ui

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import ru.development.MainStyle
import ru.development.displayErrorMessage
import ru.development.models.News
import ru.development.network.Api

@Composable
fun NewsView() {
    val list = remember { mutableStateListOf<News>() }
    var someMsg: String? by remember { mutableStateOf(null) }

    Div(attrs = {
        classes(MainStyle.column, MainStyle.newsList)
    }) {
        list.forEach { news: News ->
            NewsItemView(news)
        }
        if (list.isEmpty()) Span({
            style {
                property("margin-left", "auto")
                property("margin-right", "auto")
                fontSize(30.pt)
            }
        }) { Text("Список новостей пуст") }
    }

    LaunchedEffect(Unit) {
        val result = Api.getNews()
        if (result.isSuccess) {
            list.clear()
            list.addAll(result.getOrThrow())
        } else someMsg = result.displayErrorMessage
    }

    someMsg?.let { MessageDialog(onDisposeRequest = { someMsg = null }, it) }
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