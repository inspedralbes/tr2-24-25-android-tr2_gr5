package com.example.supportly.ui.view

import com.example.supportly.model.Message
import com.example.supportly.network.Mentoria
import com.example.supportly.network.RetrofitInstance
import retrofit2.Response

class ChatRepository(private val apiService: Mentoria = RetrofitInstance.api) {

    // Enviar un mensaje
    suspend fun sendMessage(sender: String, receiver: String, message: String): Response<Message> {
        val newMessage = Message(sender = sender, receiver = receiver, message = message, timestamp = System.currentTimeMillis().toString())
        return apiService.sendMessage(newMessage)
    }

    // Obtener mensajes entre dos usuarios
    suspend fun getMessages(user1: String, user2: String): Response<List<Message>> {
        return apiService.getMessages(user1, user2)
    }
}

