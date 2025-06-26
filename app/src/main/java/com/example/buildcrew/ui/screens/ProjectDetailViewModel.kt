package com.example.buildcrew.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buildcrew.data.model.Project
import com.example.buildcrew.data.model.JoinRequest
import com.example.buildcrew.data.repository.ProjectRepository
import com.example.buildcrew.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _project = MutableStateFlow<Project?>(null)
    val project: StateFlow<Project?> = _project
    val joinRequests = MutableStateFlow<List<JoinRequest>>(emptyList())
    val currentUserId = MutableStateFlow<String?>(null)

    fun loadProject(projectId: String) {
        viewModelScope.launch {
            projectRepository.getProjectById(projectId).collect {
                _project.value = it
            }
        }
        viewModelScope.launch {
            projectRepository.getJoinRequests(projectId).collect {
                joinRequests.value = it
            }
        }
        currentUserId.value = authRepository.getCurrentUserId()
    }

    suspend fun sendJoinRequest(projectId: String, message: String): Boolean {
        val uid = authRepository.getCurrentUserId() ?: return false
        val joinRequest = JoinRequest(userId = uid, message = message)
        return try {
            projectRepository.sendJoinRequest(projectId, joinRequest)
            true
        } catch (e: Exception) {
            false
        }
    }
} 