package com.example.buildcrew.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.buildcrew.data.model.Project
import com.example.buildcrew.data.model.JoinRequest
import com.example.buildcrew.ui.nav.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    navController: NavController,
    projectId: String,
    viewModel: ProjectDetailViewModel = hiltViewModel()
) {
    val project by viewModel.project.collectAsState(initial = null)
    val joinRequests by viewModel.joinRequests.collectAsState(initial = emptyList())
    val currentUserId by viewModel.currentUserId.collectAsState()
    var joinMessage by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(projectId) { viewModel.loadProject(projectId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(project?.title ?: "Project Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        project?.let { proj ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(proj.title, style = MaterialTheme.typography.headlineMedium)
                Text(proj.description, style = MaterialTheme.typography.bodyMedium)
                Text("Required Skills: ${proj.requiredSkills.joinToString(", ")}", style = MaterialTheme.typography.bodySmall)
                Text("Members: ${proj.members.size}", style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(16.dp))
                if (currentUserId != null && !proj.members.contains(currentUserId)) {
                    OutlinedTextField(
                        value = joinMessage,
                        onValueChange = { joinMessage = it },
                        label = { Text("Message to owner") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = {
                        scope.launch {
                            val success = viewModel.sendJoinRequest(projectId, joinMessage)
                            if (!success) error = "Failed to send join request"
                        }
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Request to Join")
                    }
                } else {
                    Text("You are a member of this project.", color = MaterialTheme.colorScheme.primary)
                }
                error?.let {
                    Spacer(Modifier.height(8.dp))
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
                Spacer(Modifier.height(16.dp))
                Button(onClick = { navController.navigate(Screen.Chat.createRoute(projectId)) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Open Project Chat")
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
} 