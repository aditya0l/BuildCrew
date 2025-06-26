package com.example.buildcrew.data.source

import com.example.buildcrew.data.model.Project
import com.example.buildcrew.data.model.JoinRequest
import com.example.buildcrew.data.repository.ProjectRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseProjectRepository(private val firestore: FirebaseFirestore) : ProjectRepository {
    override fun getProjects(): Flow<List<Project>> = callbackFlow {
        val listener = firestore.collection("projects")
            .addSnapshotListener { snapshot, _ ->
                val projects = snapshot?.toObjects(Project::class.java) ?: emptyList()
                trySend(projects)
            }
        awaitClose { listener.remove() }
    }

    override suspend fun createProject(project: Project) {
        try {
            val docRef = firestore.collection("projects").add(project).await()
            println("Project created successfully with ID: ${docRef.id}")
        } catch (e: Exception) {
            println("Error creating project: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun sendJoinRequest(projectId: String, joinRequest: JoinRequest) {
        firestore.collection("projects").document(projectId)
            .collection("joinRequests").add(joinRequest).await()
    }

    override fun getProjectById(projectId: String): Flow<Project?> = callbackFlow {
        val listener = firestore.collection("projects").document(projectId)
            .addSnapshotListener { snapshot, _ ->
                trySend(snapshot?.toObject(Project::class.java))
            }
        awaitClose { listener.remove() }
    }

    override fun getJoinRequests(projectId: String): Flow<List<JoinRequest>> = callbackFlow {
        val listener = firestore.collection("projects").document(projectId)
            .collection("joinRequests")
            .addSnapshotListener { snapshot, _ ->
                val requests = snapshot?.toObjects(JoinRequest::class.java) ?: emptyList()
                trySend(requests)
            }
        awaitClose { listener.remove() }
    }
} 