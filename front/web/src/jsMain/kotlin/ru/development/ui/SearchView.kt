package ru.development.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import ru.development.MainStyle

@Composable
fun SearchView(onInput: (String) -> Unit) {
    Div(attrs = { classes(MainStyle.row, MainStyle.search) }) {
        Span(attrs = {
            style {
                color(Color("rgba(0, 0, 0, 0.70)"))
                fontSize(24.px)
                fontWeight(500)
                marginRight(30.px)
            }
        }) { Text("Поиск") }

        Input(InputType.Text, attrs = {
            onInput { onInput(it.value) }
        })
    }
}