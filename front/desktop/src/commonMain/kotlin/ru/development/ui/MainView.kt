package ru.development.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.development.ui.MainViewTab.Books
import ru.development.ui.MainViewTab.Readers
import ru.development.models.BookRegistrar
import ru.development.models.LibraryWorker
import ru.development.models.User

enum class MainViewTab(val title: String, val id: Int) {
    Books("Книги", 0), Readers("Читатели", 1),
}

private val tabsForLibraryWorker = listOf(Books, Readers)
private val tabsForBookRegistrar = listOf(Books)

@Composable
fun BoxScope.MainView(user: User, signOut: () -> Unit) {
    var currentTab by remember { mutableStateOf(Books) }

    val isLibraryWorker = remember { user.type is LibraryWorker }
    val isBookRegistrar = remember { user.type is BookRegistrar }

    val usedTabs = if (isLibraryWorker) tabsForLibraryWorker else tabsForBookRegistrar

    var dropDownExpanded by remember { mutableStateOf(false) }

    Column {
        Row {
            TabRow(selectedTabIndex = usedTabs.indexOf(currentTab), modifier = Modifier.weight(1F)) {
                usedTabs.forEach { tab ->
                    Tab(
                        modifier = Modifier.height(50.dp),
                        selected = tab == currentTab,
                        onClick = { currentTab = tab },
                    ) {
                        Text(tab.title)
                    }
                }
            }
            Box(modifier = Modifier.width(50.dp)) {
                IconButton(onClick = { dropDownExpanded = !dropDownExpanded }) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "More"
                    )
                }
                DropdownMenu(expanded = dropDownExpanded, onDismissRequest = { dropDownExpanded = false }) {
                    DropdownMenuItem(onClick = signOut) { Text("Выйти")  }
                }
            }
        }

        when (currentTab) {
            Books -> BooksListView(isLibraryWorker, isBookRegistrar)
            Readers -> ReadersListView()
        }
    }
}
