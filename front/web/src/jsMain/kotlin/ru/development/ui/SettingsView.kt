package ru.development.ui

import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import ru.development.MainStyle
import ru.development.displayErrorMessage
import ru.development.models.User
import ru.development.network.Api

@Composable
fun SettingsView(user: User) {
    val scope = rememberCoroutineScope()

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    var someMsg: String? by remember { mutableStateOf(null) }

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
        Spacer(height = 20.px)
        InputWithTitle("Старый пароль", InputType.Password, onInput = { oldPassword = it })
        Spacer(height = 20.px)
        InputWithTitle("Новый пароль", InputType.Password, onInput = { newPassword = it })
        Spacer(height = 20.px)
        ButtonWithText("Сменить") {
            scope.launch {
                val res = Api.changePassword(user.id, oldPassword, newPassword)
                if (res.isSuccess) {
                    oldPassword = ""
                    newPassword = ""
                    someMsg = "Пароль успешно изменен"
                }
                else someMsg = res.displayErrorMessage
            }
        }
    }

    someMsg?.let { MessageDialog(onDisposeRequest = { someMsg = null }, it) }
}