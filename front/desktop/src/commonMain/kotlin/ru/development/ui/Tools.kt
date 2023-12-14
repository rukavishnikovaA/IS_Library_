package ru.development.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SearchPanel(query: String, onValueChange: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Поиск")

        Spacer(modifier = Modifier.width(20.dp))

        OutlinedTextField(
            value = query,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier.weight(1F).padding(0.dp)
        )
    }
}


@Composable
fun TextButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick, modifier) { Text(text) }
}