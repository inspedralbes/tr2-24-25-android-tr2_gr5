package com.example.supportly.ui.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supportly.model.Message
import com.example.supportly.network.RetrofitInstance.api
import kotlinx.coroutines.launch

class ChatViewModel(private val chatRepository: ChatRepository) : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    fun sendMessage(sender: String, receiver: String, message: String) {
        viewModelScope.launch {
            try {
                val response = chatRepository.sendMessage(sender, receiver, message)
                if (response.isSuccessful) {
                    _messages.value = _messages.value?.plus(response.body()!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadMessages(user1: String, user2: String) {
        viewModelScope.launch {
            try {
                val response = chatRepository.getMessages(user1, user2)
                if (response.isSuccessful) {
                    _messages.value = response.body()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


