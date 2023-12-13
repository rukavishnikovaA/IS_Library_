package ru.development.ui

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import ru.development.MainStyle
import ru.development.displayErrorMessage
import ru.development.models.BookOrder
import ru.development.models.BookOrder.Companion.sortedByFiledIndex
import ru.development.models.User
import ru.development.network.Api


@Composable
fun MyBooksView(user: User) {

    val items = remember { mutableStateListOf<BookOrder>() }

    var errMsg: String? by remember { mutableStateOf(null) }

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
        if (items.isEmpty()) Text("Список книг пуст")
        else MyBooksView(items)
    }

    LaunchedEffect(Unit) {
        val result = Api.getBookOrderList(user.id)
        if (result.isSuccess) {
            items.clear()
            items.addAll(result.getOrThrow())
        } else {
            errMsg = result.displayErrorMessage
        }
    }
}


@Composable
fun MyBooksView(items: List<BookOrder>) {
    var searchQuery by remember { mutableStateOf("") }

    var sortedIndex: Int? by remember { mutableStateOf(null) }

    SearchView(onInput = { searchQuery = it })

    Table(attrs = {
        style { border(1.px) }
    }) {
        TableHeader(
            "Название книги",
            "Автор",
            "Год",
            "Описание",
            "Когда выдана",
            "Срок сдачи",
            onClickCell = { index ->
                sortedIndex = if (sortedIndex == index) null else index
            }
        )
        items.filter { BookOrder.filterCondition(searchQuery, it) }
            .sortedByFiledIndex(sortedIndex)
            .forEach { item ->
                TableRow(
                    item.book.name,
                    item.book.author,
                    item.book.year,
                    item.book.description,
                    item.info.displayDateStart,
                    item.info.displayDateEnd,
                )
            }
    }
}
