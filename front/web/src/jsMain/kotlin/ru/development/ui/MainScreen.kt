package ru.development.ui

import Container
import androidx.compose.runtime.*
import kotlinx.datetime.internal.JSJoda.use
import ru.development.models.User
import ru.development.ui.MenuItem.*


@Composable
fun MainScreen(user: User?, goToAuth: () -> Unit, reload: (exit: Boolean) -> Unit) {
    val items = if (user == null) notAuthedItems else authedItems

    var selectedItem: MenuItem by remember { mutableStateOf(MenuItem.entries.first()) }

    Container(
        items = items,
        user = user,
        selectedItem = selectedItem,
        onClick = { selectedItem = it },
        onExit = { reload(true) },
        onAuth = goToAuth,
    ) {
        when (selectedItem) {
            News -> NewsView()
            Library -> LibraryView(user)
            MyBooks -> MyBooksView(user!!)
            Settings -> SettingsView(user!!)
        }
    }
}

