package com.example.supportly.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.supportly.model.Message
import com.example.supportly.network.RetrofitInstance.api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UserChatScreen(correuAlumne: String?, nom: String?) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<Message>()) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header del chat
        Text(
            text = "Chat con $nom",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de mensajes
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            items(messages) { message ->
                ChatMessageItem(message)
            }
        }

        // Barra de escritura y botón de enviar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                singleLine = true,
                placeholder = { Text(text = "Escribe un mensaje...") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    if (messageText.isNotEmpty()) {
                        sendMessageToUser(
                            sender = correuAlumne ?: "",
                            receiver = nom ?: "",
                            messageText = messageText,
                            messages = messages,
                            updateMessages = { updatedMessages -> messages = updatedMessages },
                            coroutineScope = coroutineScope
                        )
                        messageText = "" // Limpiar el campo de texto
                    }
                })
            )

            IconButton(
                onClick = {
                    if (messageText.isNotEmpty()) {
                        sendMessageToUser(
                            sender = correuAlumne ?: "",
                            receiver = nom ?: "",
                            messageText = messageText,
                            messages = messages,
                            updateMessages = { updatedMessages -> messages = updatedMessages },
                            coroutineScope = coroutineScope
                        )
                        messageText = "" // Limpiar el campo de texto
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Enviar mensaje"
                )
            }
        }
    }

    // Cargar mensajes al abrir el chat
    LaunchedEffect(Unit) {
        fetchMessages(
            user1 = correuAlumne ?: "",
            user2 = nom ?: "",
            updateMessages = { fetchedMessages -> messages = fetchedMessages }
        )
    }
}

// Composable para mostrar un mensaje
@Composable
fun ChatMessageItem(message: Message) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 2.dp
    ) {
        Text(
            text = "${message.sender}: ${message.message}",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.body1
        )
    }
}

// Función para enviar un mensaje al servidor
fun sendMessageToUser(
    sender: String,
    receiver: String,
    messageText: String,
    messages: List<Message>,
    updateMessages: (List<Message>) -> Unit,
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch {
        try {
            val response = api.sendMessage(
                Message(sender = sender, receiver = receiver, message = messageText, timestamp = "")
            )
            if (response.isSuccessful) {
                val sentMessage = response.body()
                sentMessage?.let {
                    // Actualizar la lista de mensajes con el nuevo mensaje
                    updateMessages(messages + it)
                }
            } else {
                println("Error al enviar mensaje: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error de red: ${e.localizedMessage}")
        }
    }
}

// Función para cargar mensajes del servidor
fun fetchMessages(
    user1: String,
    user2: String,
    updateMessages: (List<Message>) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = api.getMessages(user1 = user1, user2 = user2)
            if (response.isSuccessful) {
                val fetchedMessages = response.body()
                fetchedMessages?.let {
                    // Actualizar la lista de mensajes con los mensajes obtenidos
                    updateMessages(it)
                }
            } else {
                println("Error al obtener mensajes: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error de red: ${e.localizedMessage}")
        }
    }
}
