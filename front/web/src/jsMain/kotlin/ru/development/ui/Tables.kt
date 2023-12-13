package ru.development.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.href
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.marginRight
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLTableRowElement
import ru.development.MainStyle


@Composable
fun TableHeader(vararg headers: String, onClickCell: (index: Int) -> Unit) {
    Tr {
        headers.forEach { name ->
            Th { TableRow(name, onClickCell = onClickCell) }
        }
    }
}

@Composable
fun TableRow(vararg values: String, onClickCell: ((index: Int) -> Unit)? = null) {

    Tr {
        values.forEachIndexed { index, name ->
            Td {
                Span({
                    if (onClickCell != null) classes(MainStyle.hovered)

                    onClick { onClickCell?.invoke(index) }
                    style {
                        marginRight(20.px)
                        marginLeft(20.px)
                    }
                }) { Text(name) }
            }
        }
    }

}

@Composable
fun TableRow2(vararg values: String, onValue: @Composable (String, Int) -> Unit) {
    Tr {
        values.forEachIndexed { index, name ->
            Td {
                onValue(name, index)
            }
        }
    }
}