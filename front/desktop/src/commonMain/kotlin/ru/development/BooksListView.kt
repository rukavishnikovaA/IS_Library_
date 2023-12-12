package ru.development

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import ru.development.models.BookInfo
import ru.development.network.Api

@Composable
fun BooksListView(isLibraryWorker: Boolean, isBookRegistrar: Boolean) {
    val coroutineScope = rememberCoroutineScope()

    val booksList = remember { mutableStateListOf<BookInfo>() }

    var errorMsg: String? by remember { mutableStateOf(null) }

    BooksListView(
        books = booksList,
        isLibraryWorker = isLibraryWorker,
        isBookRegistrar = isBookRegistrar,
        onUpdateData = {
            coroutineScope.launch {
                val res = Api.getBooks()
                if (res.isSuccess) {
                    booksList.clear()
                    booksList.addAll(res.getOrThrow())
                } else errorMsg = (res.exceptionOrNull() ?: UnknownError()).message
            }
        }
    )

    LaunchedEffect(Unit) {
        val res = Api.getBooks()
        if (res.isSuccess) {
            booksList.clear()
            booksList.addAll(res.getOrThrow())
        } else errorMsg = (res.exceptionOrNull() ?: UnknownError()).message
    }

    errorMsg?.let { msg ->
        DialogWithMessage(msg, onDismissRequest = { errorMsg = null })
    }
}

@Composable
fun BooksListView(books: List<BookInfo>, isLibraryWorker: Boolean, isBookRegistrar: Boolean, onUpdateData: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    var selectedItem: BookInfo? by remember { mutableStateOf(null) }

    var showAddBookDialog by remember { mutableStateOf(false) }

    var showCreateOrderDialog by remember { mutableStateOf(false) }

    var editableBook: BookInfo? by remember { mutableStateOf(null) }

    val scope = rememberCoroutineScope()

    var errorMsg: String? by remember { mutableStateOf(null) }

    var sortedIndex: Int? by remember { mutableStateOf(null) }

    Column(modifier = Modifier.background(Color.White).padding(horizontal = 20.dp)) {

        Spacer(Modifier.height(20.dp))

        SearchPanel(query = searchQuery, onValueChange = { searchQuery = it })

        Spacer(Modifier.height(20.dp))


        Column(Modifier.weight(1F)) {
            TableRow(
                "Название",
                "Автор",
                "Год выпуска",
                "Издательство",
                "Описание",
                "Жанр",
                "Язык",
                "В наличии (шт,)",
                "Количество страниц",
                isSelected = false,
                onClickCell = { index ->
                    sortedIndex = if (index == sortedIndex) null
                    else index
                }
            )
            books.filter { filterBookCondition(it, searchQuery) }
                .sortedByFiledIndex(sortedIndex)
                .forEach { book ->
                    TableRow(
                        book.name,
                        book.author,
                        book.year,
                        book.factory,
                        book.description,
                        book.janre,
                        book.language,
                        book.count.toString(),
                        book.pageCount.toString(),
                        onClickRow = { selectedItem = if (selectedItem == book) null else book },
                        isSelected = selectedItem == book
                    )
                }
        }
        Spacer(Modifier.height(20.dp))

        Column {
            selectedItem?.let { selected ->
                Row {
                    if (isLibraryWorker) TextButton("Выдать", onClick = { showCreateOrderDialog = true })
                    else if (isBookRegistrar) {
                        TextButton("Редактировать", onClick = {
                            editableBook = selected
                            showAddBookDialog = true
                        })
                        Spacer(Modifier.width(20.dp))
                        TextButton(
                            text = "Удалить",
                            onClick = {
                                scope.launch {
                                    val result = Api.deleteBook(selected.id)
                                    if (result.isSuccess) {
                                        selectedItem = null
                                        onUpdateData()
                                    } else errorMsg = "Произошла ошибка при удалении книги"
                                }
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(20.dp))

            if (isBookRegistrar && selectedItem == null) TextButton(
                text = "Добавить книгу",
                onClick = { showAddBookDialog = true }
            )

            Spacer(Modifier.height(20.dp))
        }
    }

    if (showAddBookDialog) AddBookDialog(
        onDismissRequest = {
            editableBook = null
            showAddBookDialog = false
        },
        existed = editableBook,
        onUpdateData = onUpdateData
    )

    selectedItem?.let { item ->
        if (showCreateOrderDialog) OrderBookDialog(
            bookInfo = item,
            onDismissRequest = { showCreateOrderDialog = false },
            onUpdateData = onUpdateData,
        )
    }

    errorMsg?.let { msg ->
        DialogWithMessage(message = msg, onDismissRequest = { errorMsg = null })
    }
}

fun filterBookCondition(book: BookInfo, query: String): Boolean {
    return book.toString().lowercase().contains(query.lowercase())
}

@Composable
fun TableRow(
    vararg value: String,
    isSelected: Boolean,
    rowModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    onClickRow: (() -> Unit)? = null,
    onClickCell: ((index: Int) -> Unit)? = null,
) {
    Row(modifier = rowModifier.fillMaxWidth()
        .run {
            if (onClickRow != null) clickable { onClickRow.invoke() }
            else this
        }
        .run {
            if (isSelected) background(Color.Cyan)
            else this
        }
        .padding(top = 10.dp)
        .padding(horizontal = 20.dp)
    ) {
        value.forEachIndexed { index, title ->
            Text(
                text = title,
                modifier = textModifier.weight(1F)
                    .height(40.dp)
                    .run {
                        if (onClickCell != null) clickable { onClickCell.invoke(index) }
                        else this
                    }
            )
        }
    }
    Divider()
}

@Composable
fun AddBookDialog(
    onDismissRequest: () -> Unit,
    onUpdateData: () -> Unit,
    existed: BookInfo?
) = Dialog(onDismissRequest = onDismissRequest) {
    var bookname by remember { mutableStateOf(existed?.name ?: "") }
    var author by remember { mutableStateOf(existed?.author ?: "") }
    var factory by remember { mutableStateOf(existed?.factory ?: "") }
    var description by remember { mutableStateOf(existed?.description ?: "") }
    var year by remember { mutableStateOf(existed?.year ?: "") }
    var janre by remember { mutableStateOf(existed?.janre ?: "") }
    var language by remember { mutableStateOf(existed?.language ?: "") }
    var count by remember { mutableStateOf(existed?.count ?: 0) }
    var pageCount by remember { mutableStateOf(existed?.pageCount?.toString() ?: "") }
    var downloadAddress by remember { mutableStateOf(existed?.downloadAddress ?: "") }

    var errorMsg: String? by remember { mutableStateOf(null) }

    val scrollState = rememberScrollState()

    val coroutineScope = rememberCoroutineScope()


    Column(
        Modifier.background(Color.White, RoundedCornerShape(16.dp))
            .padding(40.dp)
            .height(500.dp)
            .verticalScroll(scrollState)
    ) {
        TitleWithInput(title = "Название", value = bookname, onValueChange = { bookname = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Автор", value = author, onValueChange = { author = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Издательство", value = factory, onValueChange = { factory = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Описание", value = description, onValueChange = { description = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Год", value = year, onValueChange = { year = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Жанр", value = janre, onValueChange = { janre = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Язык", value = language, onValueChange = { language = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        Counter(title = "Количество", value = count, onValueChange = { count = it })
        Spacer(Modifier.height(20.dp))
        Divider()
        Spacer(Modifier.height(20.dp))

        TitleWithInput(title = "Количество страниц", value = pageCount, onValueChange = { pageCount = it })
        Spacer(Modifier.height(20.dp))

        TitleWithInput(
            title = "Ссылка на электронную версию",
            value = downloadAddress,
            onValueChange = { downloadAddress = it })
        Divider()
        Spacer(Modifier.height(20.dp))

        Row {
            TextButton("Сохранить", onClick = {
                val pageCountInt = pageCount.toIntOrNull()
                if (pageCountInt == null) {
                    errorMsg = "Неверно введено количество странц"
                } else {
                    val book = BookInfo(
                        id = existed?.id ?: -1,
                        name = bookname,
                        author = author,
                        factory = factory,
                        description = description,
                        year = year,
                        janre = janre,
                        language = language,
                        count = count,
                        pageCount = pageCountInt,
                        downloadAddress = downloadAddress
                    )

                    coroutineScope.launch {
                        val result = Api.addBook(book)
                        if (result.isSuccess) {
                            onUpdateData()
                            onDismissRequest()
                        } else {
                            errorMsg = (result.exceptionOrNull() ?: UnknownError()).message
                        }
                    }
                }

            })
            Spacer(Modifier.width(20.dp))
            TextButton("Отмена", onClick = onDismissRequest)
        }
    }

    errorMsg?.let { msg -> DialogWithMessage(msg, onDismissRequest = { errorMsg = null }) }
}

@Composable
fun Counter(title: String, value: Int, onValueChange: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(title)
        Spacer(Modifier.width(10.dp))
        Text(value.toString(), Modifier.width(100.dp))
        Spacer(Modifier.width(10.dp))

        Column {
            TextButton("+", { onValueChange(value + 1) })
            Spacer(Modifier.height(5.dp))
            TextButton("-", { onValueChange(value - 1) })
        }
    }
}

private fun List<BookInfo>.sortedByFiledIndex(index: Int?): List<BookInfo> {
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