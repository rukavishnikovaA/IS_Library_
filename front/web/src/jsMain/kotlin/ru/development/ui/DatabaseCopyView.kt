package ru.development.ui

import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import kotlinx.datetime.internal.JSJoda.use
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import ru.development.MainStyle
import ru.development.displayErrorMessage
import ru.development.models.User
import ru.development.network.Api

@Composable
fun DatabaseCopyView(user: User) {
    var someMsg: String? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

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
        ButtonWithText("Копировать базу данных", attrs = {
            style {
                alignSelf(AlignSelf.Center)
                property("margin-top", "auto")
                property("margin-bottom", "auto")
            }
        }) {
            scope.launch {
                val result = Api.backupDatabase(user.id)
                if (result.isSuccess) someMsg = "База данных успешно скопирована!"
                else someMsg = result.displayErrorMessage
            }
        }
    }

    someMsg?.let { MessageDialog(onDisposeRequest = { someMsg = null }, it) }
}