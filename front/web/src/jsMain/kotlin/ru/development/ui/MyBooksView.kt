package ru.development.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import ru.development.MainStyle
import ru.development.models.MyBook

val myBooks = listOf(MyBook.default, MyBook.default, MyBook.default, MyBook.default, MyBook.default)

@Composable
fun MyBooksView() {
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
            TableHeader(
                "Название книги",
                "Автор",
                "Год",
                "Описание",
                "Когда выдана",
                "Срок сдачи"
            )
            myBooks.forEach { item ->
                TableItem(
                    item.bookInfo.name,
                    item.bookInfo.author,
                    item.bookInfo.year,
                    item.bookInfo.description,
                    item.dateOfStartInS.toString(),
                    item.dateOfEndInS.toString()
                )
            }
        }
    }
}
