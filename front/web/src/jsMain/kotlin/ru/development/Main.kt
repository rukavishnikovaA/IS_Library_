import androidx.compose.runtime.*
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposableInBody
import org.w3c.dom.HTMLDivElement
import ru.development.MainStyle
import ru.development.ui.AuthScreen
import ru.development.ui.MainScreen
import ru.development.tools.Title
import ru.development.ui.MenuItem
import ru.development.ui.MenuView

fun main() {
    document.addEventListener("DOMContentLoaded", {
        renderComposableInBody {
            Root()
        }
    })
}

enum class Navigation(val path: String) {
    Auth("auth"),
    News("main"),
    SystemAdmin("sysadmin")
}

private fun handlePath(): Navigation {
    val path = window.location.pathname

    console.log(path)

    var pathArray = path.split('/')
    if (pathArray.isNotEmpty()) pathArray = pathArray.drop(1)

    console.log(pathArray)

    if (pathArray.isEmpty()) return Navigation.Auth

    val firstLevel = pathArray.first()

    console.log(firstLevel)

    return Navigation.entries.find { it.path == firstLevel } ?: Navigation.Auth
}

@Composable
fun Root() {
    Style(MainStyle)

    var current: Navigation by remember { mutableStateOf(handlePath()) }

    when (current) {
        Navigation.Auth -> AuthScreen(goNext = { current = Navigation.News })
        Navigation.News -> MainScreen()
        Navigation.SystemAdmin -> {
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
    }
}

@Composable
fun Container(
    selectedItem: MenuItem,
    onClick: ((MenuItem) -> Unit),
    onExit: (() -> Unit),
    content: ContentBuilder<HTMLDivElement>
) {
    Container(onClick, selectedItem, onExit, content)
}

@Composable
fun Container(content: ContentBuilder<HTMLDivElement>) {
    Container(null, null, null, content)
}

@Composable
private fun Container(
    onClick: ((MenuItem) -> Unit)?,
    selectedItem: MenuItem?,
    onExit: (() -> Unit)?,
    content: ContentBuilder<HTMLDivElement>
) {
    Div(attrs = {
        classes(MainStyle.fillAbsolute, MainStyle.column)
        style {
            paddingTop(46.px)
            paddingLeft(46.px)
            paddingRight(46.px)

            background("#C9CBFF")

            overflowY("scroll")
        }
    }) {
        Div(attrs = { classes(MainStyle.row) }) {
            Title("Государственная библиотека")

            if (selectedItem != null && onExit != null && onClick != null) MenuView(selectedItem, onClick, onExit)
        }

        content(this)
    }
}