package ru.development.tools

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun Title(text: String) {
    Span(attrs = {
        style {
            fontSize(36.px)
            fontWeight(800)
            color(Color("#444FB4"))

            width(330.px)

            paddingBottom(45.px)
        }
    }) {
        Text(text)
    }
}