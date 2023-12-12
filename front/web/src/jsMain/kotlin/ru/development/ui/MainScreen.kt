package ru.development.ui

import Container
import androidx.compose.runtime.*
import ru.development.ui.MenuItem.*


@Composable
fun MainScreen() {
    var selectedItem: MenuItem by remember { mutableStateOf(MenuItem.entries.first()) }

    Container(
        selectedItem = selectedItem,
        onClick = { selectedItem = it; console.log("On click212") },
        onExit = {}
    ) {
        when (selectedItem) {
            News -> NewsView()
            Library -> LibraryView()
            MyBooks -> MyBooksView()
        }

        LaunchedEffect(selectedItem) {
            console.log("On change menu item")
        }
    }



    LaunchedEffect(selectedItem) {
        console.log("On change menu item234")
    }
}

