package com.example.supportly.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.supportly.model.Message

@Composable
fun ChatsScreen(viewModel: ChatViewModel, sender: String) {
    var receiver by remember { mutableStateOf("") }
    val messages by viewModel.messages.observeAsState(emptyList())

    Column {
        OutlinedTextField(
            value = receiver,
            onValueChange = { newValue ->
                receiver = newValue
                if (newValue.isNotEmpty()) {
                    viewModel.loadMessages(sender, receiver)
                }
            },
            label = { Text("Buscar receptor") },
            modifier = Modifier.fillMaxWidth()
        )

        // Mostrar mensajes
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages) { message ->
                Text("${message.sender}: ${message.message}")
            }
        }

        // Campo de entrada para enviar mensajes
        var messageText by remember { mutableStateOf("") }
        Row {
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                if (receiver.isNotEmpty() && messageText.isNotEmpty()) {
                    viewModel.sendMessage(sender, receiver, messageText)
                    messageText = ""
                }
            }) {
                Text("Enviar")
            }
        }
    }
}
