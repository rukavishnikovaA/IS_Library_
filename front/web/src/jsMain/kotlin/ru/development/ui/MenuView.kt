package ru.development.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.flex
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import ru.development.MainStyle
import ru.development.models.User

enum class MenuItem(val title: String) {
    News("Главная"),
    Library("Библиотечный фонд"),
    MyBooks("Мои книги"),
    Settings("Настройки")
}

val authedItems = listOf(MenuItem.News, MenuItem.Library, MenuItem.MyBooks, MenuItem.Settings)
val notAuthedItems = listOf(MenuItem.News, MenuItem.Library)

@Composable
fun MenuView(
    items: List<MenuItem>,
    user: User?,
    selectedItem: MenuItem,
    onClick: (MenuItem) -> Unit,
    onExit: () -> Unit,
    onAuth: () -> Unit
) {
    Div(attrs = {
        classes(MainStyle.row, MainStyle.menu)

        style {
            flex(1)

            marginTop(20.px)
            marginLeft(30.px)
        }
    }) {
        items.forEach { item ->
            MenuItemView(
                text = item.title,
                isSelected = selectedItem == item,
                onClick = { onClick(item) }
            )
        }

        Div(attrs = {
            style { flex(1) }
        }) { }

        if (user == null) MenuItemView(text = "Войти", isSelected = false, onClick = onAuth)
        else MenuItemView(text = "Выход", isSelected = false, onClick = onExit)
    }
}

@Composable
fun MenuItemView(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Span(attrs = {
        classes(MainStyle.item, MainStyle.fitContent)
        if (isSelected) classes(MainStyle.selected)

        onClick {
            onClick()
        }

    }) {
        Text(text)
    }
}