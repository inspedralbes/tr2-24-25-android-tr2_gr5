package com.example.supportly.model

data class Message(
    val sender: String,   // ID o nombre del usuario que envía el mensaje
    val receiver: String, // ID o nombre del usuario que recibe el mensaje
    val message: String,   // El contenido del mensaje
    val timestamp: String
)
