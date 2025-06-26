package com.example.buildcrew.data.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val uid: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "",
    val skills: List<String> = emptyList(),
    val profileImageUrl: String = "",
    val bio: String = ""
) 