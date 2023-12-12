package ru.development.ui

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
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
fun BoxScope.MainView(user: User) {
    var currentTab by remember { mutableStateOf(Books) }


    val isLibraryWorker = remember { user.type is LibraryWorker }
    val isBookRegistrar = remember { user.type is BookRegistrar }

    val usedTabs = if (isLibraryWorker) tabsForLibraryWorker else tabsForBookRegistrar

    Column {
        TabRow(selectedTabIndex = usedTabs.indexOf(currentTab)) {
            usedTabs.forEach { tab ->
                Tab(
                    modifier = Modifier.height(40.dp),
                    selected = tab == currentTab,
                    onClick = { currentTab = tab },
                ) {
                    Text(tab.title)
                }
            }
        }

        when (currentTab) {
            Books -> BooksListView(isLibraryWorker, isBookRegistrar)
            Readers -> ReadersListView()
        }
    }
}

@Composable
fun TextButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick, modifier) { Text(text) }
}