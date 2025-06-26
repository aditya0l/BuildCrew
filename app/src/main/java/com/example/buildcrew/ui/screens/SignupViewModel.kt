package com.example.buildcrew.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildcrew.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun signUpWithEmail(email: String, password: String): Boolean {
        var result = false
        viewModelScope.launch {
            result = try {
                authRepository.signUpWithEmail(email, password) != null
            } catch (e: Exception) {
                false
            }
        }
        return result
    }
} 