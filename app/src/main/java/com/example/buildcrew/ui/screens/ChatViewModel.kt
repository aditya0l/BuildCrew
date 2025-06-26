package com.example.buildcrew.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildcrew.data.model.Message
import com.example.buildcrew.data.repository.ChatRepository
import com.example.buildcrew.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    fun loadMessages(projectId: String) {
        viewModelScope.launch {
            chatRepository.getMessages(projectId).collect {
                _messages.value = it
            }
        }
    }

    suspend fun sendMessage(projectId: String, text: String) {
        val senderId = authRepository.getCurrentUserId() ?: return
        val message = Message(text = text, senderId = senderId)
        chatRepository.sendMessage(projectId, message)
    }
} 