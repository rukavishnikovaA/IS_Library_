package ru.development.ui

import Container
import androidx.compose.runtime.*
import kotlinx.browser.localStorage
import kotlinx.coroutines.launch
import kotlinx.datetime.internal.JSJoda.use
import kotlinx.serialization.encodeToString
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.set
import ru.development.MainStyle
import ru.development.Serialization
import ru.development.displayErrorMessage
import ru.development.models.User
import ru.development.network.Api

@Composable
fun AuthScreen(goNext: (User?) -> Unit) {
    Container {
        AuthInput(goNext)
    }
}

@Composable
fun AuthInput(goNext: (User?) -> Unit) {
    val scope = rememberCoroutineScope()

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var errMsg: String? by remember { mutableStateOf(null) }

    Div(attrs = {
        classes(MainStyle.authInput, MainStyle.column, MainStyle.fillAbsolute, MainStyle.fitContent)
    }) {
        Span(attrs = {
            style {
                color(Color("#222031"))
                fontSize(36.px)
                fontWeight(800)
            }
        }) { Text("Вход") }

        InputWithTitle("Email", login, InputType.Email, onInput = { login = it })
        InputWithTitle("Пароль", login, InputType.Password, onInput = { password = it })

        Spacer(height = 20.px)

        ButtonWithText("Войти", onClick = {
            scope.launch {
                val result = Api.login(login, password)

                console.log(result)

                if (result.isSuccess) {
                    val user = result.getOrThrow()
                    val userJson = Serialization.json.encodeToString(user)
                    localStorage["user"] = userJson
                    goNext(user)
                }
                else errMsg = result.displayErrorMessage
            }
        })

        Spacer(height = 20.px)

        ButtonWithText("Пропустить", onClick = { goNext(null) })

        RememberPasswordLink()
    }

    errMsg?.let { MessageDialog(onDisposeRequest = { errMsg = null }, it) }
}


@Composable
fun RememberPasswordLink() {
    var showResetPasswordDialog by remember { mutableStateOf(false) }

    A(attrs = {
        style {
            color(Color("#5057FF"))
            fontSize(20.px)
            fontWeight(500)
            marginTop(10.px)
        }

        onClick { showResetPasswordDialog = true }

    }) {
        Text("Не помню пароль")
    }

    if (showResetPasswordDialog) Dialog(onDisposeRequest = { showResetPasswordDialog = false}) {
        Text("На вашу почту выслана инструкция по восстановлению пароля!")
    }
}

@Composable
fun <T> InputWithTitle(title: String, text: String, type: InputType<T>, onInput: (T) -> Unit) {
    Div(attrs = {
        classes(MainStyle.column, MainStyle.inputWithTitle)
    }) {
        Span(attrs = {
            style {
                fontSize(24.px)
                fontWeight(500)
            }
        }) { Text(title) }

        Input(type, attrs = {
            value(text)
            onInput { event ->
                onInput(event.value)
            }
        })
    }
}

@Composable
fun Spacer(height: CSSNumeric? = null, width: CSSNumeric? = null) {
    Div(attrs = {
        style {
            height?.let { height(it) }
            width?.let { width(it) }
        }
    })
}


@Composable
fun MessageDialog(onDisposeRequest: () -> Unit, text: String) {
    Dialog(onDisposeRequest = onDisposeRequest) {
        Div(attrs = { classes(MainStyle.column) }) {
            Text(text)
            Spacer(height = 20.px)
            ButtonWithText("ОК", onClick = onDisposeRequest, attrs = {
                style {
                    property("margin-left", "auto")
                    property("margin-right", "auto")
                }
            })
        }
    }
}