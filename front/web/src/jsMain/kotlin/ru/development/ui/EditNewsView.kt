package ru.development.ui

import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import ru.development.MainStyle
import ru.development.displayErrorMessage
import ru.development.models.News
import ru.development.models.User
import ru.development.network.Api

@Composable
fun EditNewsView(user: User) {
    val scope = rememberCoroutineScope()
    var reloadCounter by remember { mutableStateOf(0) }

    val list = remember { mutableStateListOf<News>() }
    var someMsg: String? by remember { mutableStateOf(null) }

    var editableNews: News? by remember { mutableStateOf(null) }
    var showCreateNewsDialog by remember { mutableStateOf(false) }

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
        Div(attrs = {
            style { flex(1) }
        }) {
            list.forEach { news ->
                EditableNewsItemView(
                    news = news,
                    onEditRequest = {
                        editableNews = news
                        showCreateNewsDialog = true
                    },
                    onReloadRequest = { reloadCounter++ }
                )
            }
        }
        Div {
            ButtonWithText("Создать", onClick = { showCreateNewsDialog = true })
        }
    }

    LaunchedEffect(reloadCounter) {
        val result = Api.getNews()
        if (result.isSuccess) {
            list.clear()
            list.addAll(result.getOrThrow())
        } else someMsg = result.displayErrorMessage
    }

    if (showCreateNewsDialog) EditOrCreateNewsDialog(
        exist = editableNews,
        onDisposeRequest = {
            editableNews = null
            showCreateNewsDialog = false
        },
        onReloadRequest = { reloadCounter++ }
    )


    someMsg?.let { MessageDialog(onDisposeRequest = { someMsg = null }, it) }
}

@Composable
fun EditOrCreateNewsDialog(
    exist: News?,
    onDisposeRequest: () -> Unit,
    onReloadRequest: () -> Unit,
) = Dialog(onDisposeRequest) {
    var title by remember { mutableStateOf(exist?.title ?: "") }
    var title2 by remember { mutableStateOf(exist?.title2 ?: "") }
    var imageLink by remember { mutableStateOf(exist?.imgSrc ?: "") }
    var text by remember { mutableStateOf(exist?.text ?: "") }

    var someMsg: String? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

    Div(attrs = { classes(MainStyle.column) }) {
        InputWithTitle("Заголовок", title, InputType.Text) { title = it }
        Spacer(height = 20.px)
        InputWithTitle("Подаголовок", title2, InputType.Text) { title2 = it }
        Spacer(height = 20.px)
        InputWithTitle("Ссылка", imageLink, InputType.Text) { imageLink = it }
        Spacer(height = 20.px)
        InputWithTitle("Текст", text, InputType.Text) { text = it }
        Spacer(height = 20.px)

        Div(attrs = { classes(MainStyle.row) }) {
            ButtonWithText("Сохранить") {
                scope.launch {
                    val news = News(id = exist?.id ?: -1, title, title2, imageLink, text)
                    val result = Api.addNews(news)
                    if (result.isSuccess) {
                        onReloadRequest()
                        onDisposeRequest()
                    } else someMsg = result.displayErrorMessage
                }
            }
            Spacer(width = 20.px)
            ButtonWithText("Отмена") { onDisposeRequest() }
        }
    }

    someMsg?.let { MessageDialog(onDisposeRequest = { someMsg = null }, it) }
}


@Composable
fun EditableNewsItemView(news: News, onEditRequest: () -> Unit, onReloadRequest: () -> Unit) {
    val scope = rememberCoroutineScope()

    var someMsg: String? by remember { mutableStateOf(null) }

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

        Div(attrs = { classes(MainStyle.row) }) {
            ButtonWithText("Редактировать", onClick = onEditRequest)

            Spacer(width = 20.px)

            ButtonWithText("Удалить", onClick = {
                scope.launch {
                    val result = Api.deleteNews(news.id)
                    if (result.isSuccess) onReloadRequest()
                    else someMsg = result.displayErrorMessage
                }
            })
        }

    }

    someMsg?.let { MessageDialog(onDisposeRequest = { someMsg = null }, it  ) }
}