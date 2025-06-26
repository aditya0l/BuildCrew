package com.example.buildcrew.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.buildcrew.data.model.Message
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    projectId: String,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState(initial = emptyList())
    var input by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(projectId) { viewModel.loadMessages(projectId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Project Chat") },
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
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f).padding(8.dp),
                reverseLayout = true
            ) {
                items(messages.size) { idx ->
                    val msg = messages[messages.size - 1 - idx]
                    Card(Modifier.fillMaxWidth().padding(4.dp)) {
                        Column(Modifier.padding(8.dp)) {
                            Text(msg.senderId, style = MaterialTheme.typography.bodySmall)
                            Text(msg.text, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Type a message...") }
                )
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    scope.launch {
                        viewModel.sendMessage(projectId, input)
                        input = ""
                    }
                }) {
                    Text("Send")
                }
            }
        }
    }
} 