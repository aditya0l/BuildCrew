package com.example.buildcrew.ui.screens

import androidx.lifecycle.ViewModel
import com.example.buildcrew.data.model.Project
import com.example.buildcrew.data.repository.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : ViewModel() {
    val projects: Flow<List<Project>> = projectRepository.getProjects()
} 