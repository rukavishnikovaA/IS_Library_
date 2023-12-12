package ru.development.ui

import Container
import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import ru.development.MainStyle

@Composable
fun AuthScreen(goNext: () -> Unit) {
    Container {
        AuthInput(goNext)
    }
}

@Composable
fun AuthInput(goNext: () -> Unit) {
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

        InputWithTitle("Email", InputType.Email)
        InputWithTitle("Пароль", InputType.Password)

        Button(attrs = {
            onClick { goNext() }
        }) { Text("Click") }

        RememberPasswordLink()
    }

}

@Composable
fun RememberPasswordLink() {
    A(href = "", attrs = {
        style {
            color(Color("#5057FF"))
            fontSize(20.px)
            fontWeight(500)
            marginTop(10.px)
        }
    }) {
        Text("Не помнб пароль")
    }
}

@Composable
fun <T> InputWithTitle(title: String, type: InputType<T>) {
    Div(attrs = {
        classes(MainStyle.column, MainStyle.inputWithTitle)
    }) {
        Span(attrs = {
            style {
                fontSize(24.px)
                fontWeight(500)
            }
        }) { Text(title) }

        Input(type)
    }
}
