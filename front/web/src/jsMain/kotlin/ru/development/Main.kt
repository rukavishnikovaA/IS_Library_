import androidx.compose.runtime.*
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposableInBody
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.get
import ru.development.MainStyle
import ru.development.Serialization
import ru.development.models.User
import ru.development.tools.Title
import ru.development.ui.*

fun main() {
    document.addEventListener("DOMContentLoaded", {
        renderComposableInBody {
            Root()
        }
    })
}


sealed class Navigation {
    data object Auth : Navigation()
    data class Main(val user: User?) : Navigation()
    data object SysAdmin : Navigation()
}

//enum class Navigation(val path: String) {
//    Auth("auth"),
//    Main("main"),
//    SystemAdmin("sysadmin")
//}

private fun handlePath(): Navigation {
    val path = window.location.pathname

    var pathArray = path.split('/')
    if (pathArray.isNotEmpty()) pathArray = pathArray.drop(1)

    val userJson = localStorage["user"]
    val user: User? = if (userJson == null) null else Serialization.json.decodeFromString(userJson)

    if (pathArray.isEmpty()) return Navigation.Main(user)

    val firstLevel = pathArray.first()

    if (firstLevel == "admin") return Navigation.SysAdmin

    return Navigation.Main(user)
}

@Composable
fun Root() {
    Style(MainStyle)

    var currentDestination: Navigation by remember { mutableStateOf(handlePath()) }

    when (val current = currentDestination) {
        Navigation.Auth -> AuthScreen(goNext = { currentDestination = Navigation.Main(it) })
        is Navigation.Main -> MainScreen(
            user = current.user,
            goToAuth = { currentDestination = Navigation.Auth },
            reload = { exit ->
                if (exit) localStorage.clear()
                window.location.reload()
            }
        )

        Navigation.SysAdmin -> SysAdminView()
    }

    OverlayScopeProvider.render()
}

@Composable
fun Container(
    items: List<MenuItem>,
    user: User?,
    selectedItem: MenuItem,
    onClick: ((MenuItem) -> Unit),
    onExit: (() -> Unit),
    onAuth: (() -> Unit)?,
    content: ContentBuilder<HTMLDivElement>
) {
    Container(items, user, onClick, selectedItem, onExit, onAuth, content)
}

@Composable
fun Container(content: ContentBuilder<HTMLDivElement>) {
    Container(null, null, null, null, null, null, content)
}

@Composable
private fun Container(
    items: List<MenuItem>?,
    user: User?,
    onClick: ((MenuItem) -> Unit)?,
    selectedItem: MenuItem?,
    onExit: (() -> Unit)?,
    onAuth: (() -> Unit)?,
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

            if (selectedItem != null && onExit != null && onClick != null && onAuth != null && items != null) {
                MenuView(items, user, selectedItem, onClick, onExit, onAuth)
            }
        }

        content(this)
    }
}