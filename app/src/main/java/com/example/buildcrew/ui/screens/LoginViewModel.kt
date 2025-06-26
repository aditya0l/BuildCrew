package com.example.buildcrew.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildcrew.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val webClientId = "891576086647-7h7atdpjfk0gd93ehufqjjvd48em2sdh.apps.googleusercontent.com"

    fun signInWithEmail(email: String, password: String): Boolean {
        var result = false
        viewModelScope.launch {
            result = try {
                authRepository.signInWithEmail(email, password) != null
            } catch (e: Exception) {
                false
            }
        }
        return result
    }

    suspend fun signInWithGoogle(idToken: String): Boolean {
        return try {
            authRepository.signInWithGoogle(idToken) != null
        } catch (e: Exception) {
            false
        }
    }
} 