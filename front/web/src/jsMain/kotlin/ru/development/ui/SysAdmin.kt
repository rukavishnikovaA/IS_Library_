package ru.development.ui

import Container
import androidx.compose.runtime.*
import kotlinx.browser.localStorage
import kotlinx.browser.window
import ru.development.models.SysAdmin
import ru.development.models.User
import ru.development.network.Api


@Composable
fun SysAdminView() {

    var user: User? by remember { mutableStateOf(null) }

    user?.let { SysAdminView(it) }

    LaunchedEffect(Unit) {
        val login = window.prompt("Введите логин","") ?: ""
        val password = window.prompt("Введите пароль","") ?: ""

        val result = Api.login(login, password)
        if (result.isSuccess) {
            val userResult = result.getOrThrow()
            if (userResult.type is SysAdmin) user = result.getOrThrow()
            else {
                window.alert("Нет доступа!")
                window.location.reload()
            }
        }
        else {
            window.alert("Данные введенны неправильно!")
            window.location.reload()
        }

    }
}

@Composable
fun SysAdminView(user: User) {
    var selectedItem by remember { mutableStateOf(sysAdminMenuItems.first()) }

    Container(
        items = sysAdminMenuItems,
        user = user,
        selectedItem = selectedItem,
        onClick = { selectedItem = it },
        onExit = {
            localStorage.clear()
            window.location.reload()
        },
        onAuth = {}
    ) {
        when (selectedItem) {
            MenuItem.News -> NewsView()
            MenuItem.Library -> LibraryView(user)
            MenuItem.MyBooks -> MyBooksView(user)
            MenuItem.Settings -> SettingsView(user)
            MenuItem.EditNews -> EditNewsView(user)
            MenuItem.DatabaseCopy -> DatabaseCopyView(user)
        }
    }
}