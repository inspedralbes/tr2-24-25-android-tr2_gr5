package com.example.supportly.ui.view

import android.util.Log
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.supportly.model.Message
import com.example.supportly.network.RetrofitInstance.api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

@Composable
fun UserChatScreen(correuAlumne: String?, nom: String?, email: String, password: String) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<Message>()) }
    val coroutineScope = rememberCoroutineScope()

    // Establecer la conexión del socket
    val mSocket: Socket = remember {
        IO.socket("http://10.0.2.2:3000")  // Reemplaza con la URL de tu servidor Socket.IO
    }

    // Establecer los listeners de los sockets
    LaunchedEffect(Unit) {
        mSocket.connect() // Conectarse al servidor

        mSocket.on(Socket.EVENT_CONNECT) {
            Log.d("Socket", "Conectado al servidor")
        }

        mSocket.on("mRecibido") { args ->
            if (args.isNotEmpty()) {
                val newMessage = args[0] as JSONObject
                try {
                    val sender = newMessage.getString("sender")
                    val receiver = newMessage.getString("receiver")
                    val message = newMessage.getString("message")
                    val timestamp = newMessage.getString("timestamp")

                    // Crear el objeto Message y actualizar la lista
                    val messageObj = Message(sender = sender, receiver = receiver, message = message, timestamp = timestamp)
                    messages = messages + messageObj  // Agregar el mensaje recibido a la lista de mensajes

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("SocketIO", "Error al procesar el mensaje", e)
                }
            }
        }

    }

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
                            sender = email ?: "",
                            receiver = correuAlumne ?: "",
                            messageText = messageText,
                            messages = messages,
                            updateMessages = { updatedMessages -> messages = updatedMessages },
                            coroutineScope = coroutineScope,
                            mSocket = mSocket // Pasamos el socket para emitir el mensaje
                        )
                        messageText = "" // Limpiar el campo de texto
                    }
                })
            )

            IconButton(
                onClick = {
                    if (messageText.isNotEmpty()) {
                        sendMessageToUser(
                            sender = email ?: "",
                            receiver = correuAlumne ?: "",
                            messageText = messageText,
                            messages = messages,
                            updateMessages = { updatedMessages -> messages = updatedMessages },
                            coroutineScope = coroutineScope,
                            mSocket = mSocket // Pasamos el socket para emitir el mensaje
                        )
                        messageText = "" // Limpiar el campo de texto
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Enviar mensaje"
                )
            }
        }
    }

    // Cargar mensajes al abrir el chat
    LaunchedEffect(Unit) {
        fetchMessages(
            user1 = email ?: "",
            user2 = correuAlumne ?: "",
            updateMessages = { fetchedMessages -> messages = fetchedMessages }
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
    coroutineScope: CoroutineScope,
    mSocket: Socket
) {
    coroutineScope.launch {
        try {
            // Enviar mensaje al servidor usando la API
            val response = api.sendMessage(
                Message(sender = sender, receiver = receiver, message = messageText, timestamp = "")
            )
            if (response.isSuccessful) {
                val sentMessage = response.body()
                sentMessage?.let {
                    // Emitir el mensaje al servidor a través del WebSocket
                    val jsonMessage = JSONObject().apply {
                        put("sender", sender)
                        put("receiver", receiver)
                        put("message", messageText)
                        put("timestamp", System.currentTimeMillis().toString()) // Puedes usar el tiempo actual o tu timestamp
                    }
                    mSocket.emit("mRecibido", jsonMessage) // Emitir el mensaje a través de WebSocket

                    // Actualizar la lista de mensajes con el mensaje enviado
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
