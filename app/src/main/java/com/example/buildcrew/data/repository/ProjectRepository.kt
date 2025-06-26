package com.example.buildcrew.data.repository

import com.example.buildcrew.data.model.Project
import com.example.buildcrew.data.model.JoinRequest
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getProjects(): Flow<List<Project>>
    suspend fun createProject(project: Project)
    suspend fun sendJoinRequest(projectId: String, joinRequest: JoinRequest)
    fun getProjectById(projectId: String): Flow<Project?>
    fun getJoinRequests(projectId: String): Flow<List<JoinRequest>>
} 