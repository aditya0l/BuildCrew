package com.example.buildcrew.ui.screens

import androidx.compose.foundation.clickable
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
import com.example.buildcrew.ui.nav.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFeedScreen(navController: NavController, viewModel: HomeFeedViewModel = hiltViewModel()) {
    val projects by viewModel.projects.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BuildCrew") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Open Projects", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))
            projects.forEach { project ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate(Screen.ProjectDetail.createRoute(project.projectId)) }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(project.title, style = MaterialTheme.typography.titleLarge)
                        Text(project.description, style = MaterialTheme.typography.bodyMedium)
                        Text("Skills: ${project.requiredSkills.joinToString(", ")}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            Spacer(Modifier.weight(1f))
            Button(onClick = { navController.navigate(Screen.ProjectCreate.route) }, modifier = Modifier.fillMaxWidth()) {
                Text("Create New Project")
            }
        }
    }
} 