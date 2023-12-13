package ru.development.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text

@Composable
fun ButtonWithText(text: String, onClick: () -> Unit) {
    Button(attrs = {
        onClick { onClick() }
    }) {
        Text(text)
    }
}