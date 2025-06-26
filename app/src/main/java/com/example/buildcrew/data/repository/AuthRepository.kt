package com.example.buildcrew.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUserId(): String?
    fun getCurrentUser(): FirebaseUser?
    suspend fun signInWithEmail(email: String, password: String): AuthResult?
    suspend fun signUpWithEmail(email: String, password: String): AuthResult?
    suspend fun signOut()
    suspend fun signInWithGoogle(idToken: String): AuthResult?
} 