package com.example.buildcrew.data.source

import com.example.buildcrew.data.model.Message
import com.example.buildcrew.data.repository.ChatRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseChatRepository(private val firestore: FirebaseFirestore) : ChatRepository {
    override fun getMessages(projectId: String): Flow<List<Message>> = callbackFlow {
        val listener = firestore.collection("chats").document(projectId)
            .collection("messages").orderBy("timestamp")
            .addSnapshotListener { snapshot, _ ->
                val messages = snapshot?.toObjects(Message::class.java) ?: emptyList()
                trySend(messages)
            }
        awaitClose { listener.remove() }
    }

    override suspend fun sendMessage(projectId: String, message: Message) {
        firestore.collection("chats").document(projectId)
            .collection("messages").add(message).await()
    }
} 