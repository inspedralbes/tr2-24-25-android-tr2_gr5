package com.example.supportly.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange


@Composable
fun TusMuertosScreen() {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf("Hola, ¿cómo estás?", "Bien, gracias. ¿Y tú?", "Todo bien!")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título de la pantalla
        Text(
            text = "Chat",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lista de mensajes
        LazyColumn(
            modifier = Modifier
                .weight(1f) // Ocupa el espacio restante
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            items(messages) { message ->
                ChatMessageItem(message)
            }
        }

        // Barra de entrada de texto
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Campo de texto para escribir el mensaje
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                singleLine = true,
                placeholder = { Text(text = "Escribe un mensaje...") }
            )

            // Botón de enviar mensaje
            IconButton(
                onClick = {
                    if (messageText.isNotEmpty()) {
                        // Añadir el mensaje a la lista (aquí puedes conectar con la lógica de tu backend)
                        messages = messages + messageText
                        messageText = "" // Limpiar el campo de texto después de enviar
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
}

@Composable
fun ChatMessageItem(message: String) {
    // Este composable se encarga de mostrar un solo mensaje
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 2.dp
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.body1
        )
    }
}
