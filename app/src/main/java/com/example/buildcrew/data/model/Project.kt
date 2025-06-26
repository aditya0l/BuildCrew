package com.example.buildcrew.data.model

import com.google.firebase.firestore.DocumentId

// Project model for Firestore

data class Project(
    @DocumentId val projectId: String = "",
    val title: String = "",
    val description: String = "",
    val createdBy: String = "",
    val requiredSkills: List<String> = emptyList(),
    val members: List<String> = emptyList()
) 