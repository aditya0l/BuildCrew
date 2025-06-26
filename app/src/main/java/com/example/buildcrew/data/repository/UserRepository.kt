package com.example.buildcrew.data.repository

import com.example.buildcrew.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(uid: String): User?
    suspend fun createUser(user: User)
    suspend fun updateUser(user: User)
    fun getCurrentUserFlow(): Flow<User?>
} 