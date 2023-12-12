package ru.development.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.marginRight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*


@Composable
fun TableHeader(vararg headers: String) {
    Tr {
        headers.forEach { name ->
            Th { TableItem(name) }
        }
    }
}

@Composable
fun TableItem(vararg values: String) {

    Tr {
        values.forEach { name ->
            Td {

                Span({
                    style {
                        marginRight(20.px)
                        marginLeft(20.px)
                    }
                }) { Text(name) }
            }
        }
    }

}