package ru.development.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLButtonElement

@Composable
fun ButtonWithText(text: String, attrs: (AttrsScope<HTMLButtonElement>.() -> Unit)? = null, onClick: () -> Unit) {
    Button(attrs = {
        onClick { onClick() }
        attrs?.invoke(this)
    }) {
        Text(text)
    }
}