package com.example.buildcrew.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildcrew.data.model.User
import com.example.buildcrew.data.repository.UserRepository
import com.example.buildcrew.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSetupViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    suspend fun saveProfile(name: String, bio: String, role: String, skills: List<String>, profileImageUrl: String): Boolean {
        return try {
            val uid = authRepository.getCurrentUserId()
            println("Current user ID: $uid")
            if (uid == null) {
                println("No current user ID found")
                return false
            }
            
            val currentUser = authRepository.getCurrentUser()
            val email = currentUser?.email ?: ""
            println("User email: $email")
            
            val user = User(
                uid = uid,
                name = name,
                email = email,
                bio = bio,
                role = role,
                skills = skills,
                profileImageUrl = profileImageUrl
            )
            println("Creating user: $user")
            
            userRepository.createUser(user)
            println("Profile saved successfully")
            true
        } catch (e: Exception) {
            println("Error saving profile: ${e.message}")
            e.printStackTrace()
            false
        }
    }
} 