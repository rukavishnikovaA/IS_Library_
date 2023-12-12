package ru.development.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import ru.development.MainStyle
import ru.development.models.BookInfo

val items = listOf(BookInfo.default, BookInfo.default, BookInfo.default, BookInfo.default, BookInfo.default)

@Composable
fun LibraryView() {
    Div(attrs = {
        classes(MainStyle.column)
        style {
            flex(1)

            backgroundColor(Color.white)
            borderRadius(10.px)
            paddingTop(56.px)
            paddingLeft(42.px)
            paddingRight(42.px)
        }
    }) {
        SearchView()

        Table(attrs = {
            style { border(1.px) }
        }) {
            TableHeader("Название книги", "Автор", "Год", "Описание", "В наличии в библиотеке")
            items.forEach { item ->
                TableItem(item.name, item.author, item.year, item.description, item.isExist.toString())
            }
        }
    }
}
