package ru.development

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ru.development.ui.Root


fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 1200.dp, height = 960.dp)
    ) {
        Root(exit = { exitApplication() })
    }
}
