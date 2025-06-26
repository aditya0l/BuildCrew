package com.example.buildcrew.data.model

import com.google.firebase.firestore.DocumentId

data class JoinRequest(
    @DocumentId val requestId: String = "",
    val userId: String = "",
    val message: String = "",
    val status: String = "pending" // pending, accepted, rejected
) 