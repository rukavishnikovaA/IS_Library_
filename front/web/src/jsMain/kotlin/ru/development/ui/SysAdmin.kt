package ru.development.ui

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.CheckboxInput
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import ru.development.MainStyle

@Composable
fun SysAdminView() {
    Div(attrs = { classes(MainStyle.column) }) {

        Span(attrs = {
            style {
                fontSize(34.px)
                fontWeight(800)
            }
        }) { Text("ПАНЕЛЬ СИСТЕМНОГО АДМИНИСТРАТОРА!") }
        Div(attrs = {
            classes(MainStyle.row)
            style {
                alignItems(AlignItems.Center)
            }
        }) {

            var isChecked by remember { mutableStateOf(false) }

            CheckboxInput(checked = isChecked) {
                onClick { isChecked = !isChecked }
            }
            Span { Text("я уебан") }
        }
    }
}