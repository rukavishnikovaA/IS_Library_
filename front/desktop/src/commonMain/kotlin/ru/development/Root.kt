package ru.development

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.development.models.User


@Composable
fun Root(exit: () -> Unit) {
    var currentRoute: Route by remember { mutableStateOf(Route.Auth) }

    Box(Modifier.fillMaxSize().background(Color.LightGray)) {
        when (val route = currentRoute) {
            Route.Auth -> AuthView(
                exit = exit,
                onSignIn = { user -> currentRoute = Route.Main(user) }
            )

            is Route.Main -> MainView(route.user)
        }
    }
}

sealed class Route {
    data object Auth : Route()
    data class Main(val user: User) : Route()
}
