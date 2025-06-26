package com.example.buildcrew.data.source

import com.example.buildcrew.data.model.User
import com.example.buildcrew.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseUserRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : UserRepository {
    override suspend fun getUser(uid: String): User? {
        return firestore.collection("users").document(uid).get().await().toObject(User::class.java)
    }

    override suspend fun createUser(user: User) {
        try {
            firestore.collection("users").document(user.uid).set(user).await()
            println("User created successfully: ${user.uid}")
        } catch (e: Exception) {
            println("Error creating user: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun updateUser(user: User) {
        firestore.collection("users").document(user.uid).set(user).await()
    }

    override fun getCurrentUserFlow(): Flow<User?> = callbackFlow {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            trySend(null)
            close()
        } else {
            val listener = firestore.collection("users").document(uid)
                .addSnapshotListener { snapshot, _ ->
                    trySend(snapshot?.toObject(User::class.java))
                }
            awaitClose { listener.remove() }
        }
    }
} 