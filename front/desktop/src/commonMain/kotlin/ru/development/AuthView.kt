package ru.development

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import ru.development.models.Reader
import ru.development.models.User
import ru.development.models.UserType
import ru.development.network.Api
import ru.development.network.NotFoundError


@Composable
fun BoxScope.AuthView(exit: () -> Unit, onSignIn: (User) -> Unit) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    var errorMsg: String? by remember { mutableStateOf(null) }
//    var showNotFoundMessage by remember { mutableStateOf(false) }
//    var showReaderHasNotAccessMessage by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.width(400.dp)
            .align(Alignment.Center)
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(vertical = 20.dp, horizontal = 40.dp)
    ) {

        TitleWithInput(
            modifier = Modifier.padding(top = 20.dp),
            title = "Логин",
            value = login,
            onValueChange = { login = it }
        )

        TitleWithInput(
            modifier = Modifier.padding(top = 20.dp),
            title = "Пароль",
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(60.dp))

        Row {
            Button(
                onClick = {
                    scope.launch {
                        val result = Api.login(login, password)
                        if (result.isSuccess) {
                            val user = result.getOrThrow()

                            if (user.type is Reader) errorMsg = "Доступ запрещен, читателю тут не рады"
                            else onSignIn(result.getOrThrow())
                        } else when (val t = result.exceptionOrNull() ?: UnknownError()) {
                            is NotFoundError -> { errorMsg = "Данные не найдены" }
                            else -> errorMsg = t.message ?: t.javaClass.name
                        }
                    }
                },
                modifier = Modifier.weight(2f)
            ) { Text("Вход") }

            Spacer(Modifier.weight(1F))

            Button(
                onClick = exit,
                modifier = Modifier.weight(2f)
            ) { Text("Выйти") }
        }
    }

    errorMsg?.let { msg -> DialogWithMessage(msg, onDismissRequest = { errorMsg = null }) }
}

@Composable
fun DialogWithMessage(message: String, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier.background(Color.White).padding(vertical = 20.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(message)
            Spacer(Modifier.height(40.dp))
            Button(onClick = onDismissRequest) { Text("Ok") }
        }
    }
}

@Composable
fun TitleWithInput(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(title, modifier = Modifier.width(100.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            visualTransformation = visualTransformation
        )
    }
}