package com.example.buildcrew.data.repository

import com.example.buildcrew.data.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getMessages(projectId: String): Flow<List<Message>>
    suspend fun sendMessage(projectId: String, message: Message)
} 