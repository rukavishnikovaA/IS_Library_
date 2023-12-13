package ru.development.ui

import androidx.compose.runtime.*
import kotlinx.datetime.internal.JSJoda.use
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.href
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import ru.development.MainStyle
import ru.development.displayErrorMessage
import ru.development.models.BookInfo
import ru.development.models.BookInfo.Companion.sortedByFiledIndex
import ru.development.models.User
import ru.development.network.Api

@Composable
fun LibraryView(user: User?) {

    val items = remember { mutableStateListOf<BookInfo>() }
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
        BooksTableView(user, items)
    }

    LaunchedEffect(Unit) {
        val result = Api.getBooks()
        if (result.isSuccess) {
            items.clear()
            items.addAll(result.getOrThrow())
        } else {
            errMsg = result.displayErrorMessage
        }
    }

    errMsg?.let { MessageDialog(onDisposeRequest = { errMsg = null }, it) }
}


val authedItemsBook = arrayOf("Название книги", "Автор", "Год", "Описание", "В наличии в библиотеке", "Ссылка")
val notAuthedItemsBook = arrayOf("Название книги", "Автор", "Год", "Описание", "В наличии в библиотеке")

@Composable
fun BooksTableView(user: User?, items: List<BookInfo>) {
    var searchQuery by remember { mutableStateOf("") }
    SearchView(onInput = { searchQuery = it })

    var sortedIndex: Int? by remember { mutableStateOf(null) }

    Table(attrs = {
        style { border(1.px) }
    }) {


        val headers = if (user == null) notAuthedItemsBook else authedItemsBook

        TableHeader(headers = headers, onClickCell = {
            sortedIndex = if (sortedIndex == it) null
            else it
        })
        items.filter { BookInfo.filterCondition(it, searchQuery) }
            .sortedByFiledIndex(sortedIndex)
            .forEach { item ->
                val columns = if (user == null) arrayOf(
                    item.name,
                    item.author,
                    item.year,
                    item.description,
                    (item.count > 0).toString()
                ) else arrayOf(
                    item.name,
                    item.author,
                    item.year,
                    item.description,
                    (item.count > 0).toString(),
                    item.downloadAddress ?: ""
                )

                TableRow2(values = columns) { name, index ->
                    val isLink = index == 5

                    if (isLink) A(attrs = {
                        name.takeIf { it.isNotBlank() }?.run {
                            href(this)
                            target(ATarget.Blank)
                            attr("rel", "noopener noreferrer")
                        }

                    }) {
                        Text("Ссылка".takeIf { name.isNotBlank() } ?: "-")
                    }
                    else Span({
                        style {
                            marginRight(20.px)
                            marginLeft(20.px)
                        }
                    }) { Text(name) }
                }


            }
    }
}