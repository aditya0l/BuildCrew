package com.example.buildcrew.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildcrew.data.model.Project
import com.example.buildcrew.data.repository.ProjectRepository
import com.example.buildcrew.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectCreateViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    suspend fun createProject(title: String, description: String, requiredSkills: List<String>): Boolean {
        return try {
            val uid = authRepository.getCurrentUserId()
            println("Creating project for user: $uid")
            if (uid == null) {
                println("No current user ID found")
                return false
            }
            
            val project = Project(
                title = title,
                description = description,
                createdBy = uid,
                requiredSkills = requiredSkills,
                members = listOf(uid)
            )
            println("Creating project: $project")
            
            projectRepository.createProject(project)
            println("Project created successfully")
            true
        } catch (e: Exception) {
            println("Error creating project: ${e.message}")
            e.printStackTrace()
            false
        }
    }
} 