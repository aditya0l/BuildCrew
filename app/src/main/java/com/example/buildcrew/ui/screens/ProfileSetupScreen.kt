package com.example.buildcrew.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.buildcrew.data.model.User
import com.example.buildcrew.ui.nav.Screen
import kotlinx.coroutines.launch

val roles = listOf("Founder", "Developer", "Designer", "Marketer", "Other")
val skillsList = listOf("Kotlin", "Java", "UI/UX", "Firebase", "Backend", "Frontend", "ML", "Other")

@Composable
fun ProfileSetupScreen(navController: NavController, viewModel: ProfileSetupViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf(roles.first()) }
    var selectedSkills by remember { mutableStateOf(listOf<String>()) }
    var profileImageUrl by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Profile Setup", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        DropdownMenuBox(
            label = "Role",
            options = roles,
            selectedOption = selectedRole,
            onOptionSelected = { selectedRole = it }
        )
        Spacer(Modifier.height(8.dp))
        MultiSelectBox(
            label = "Skills",
            options = skillsList,
            selectedOptions = selectedSkills,
            onSelectionChanged = { selectedSkills = it }
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = profileImageUrl,
            onValueChange = { profileImageUrl = it },
            label = { Text("Profile Image URL") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                val success = viewModel.saveProfile(
                    name, bio, selectedRole, selectedSkills, profileImageUrl
                )
                if (success) navController.navigate(Screen.Home.route) else error = "Profile save failed"
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Save Profile")
        }
        error?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun DropdownMenuBox(label: String, options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth().clickable { expanded = true },
            readOnly = true
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    onOptionSelected(option)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun MultiSelectBox(label: String, options: List<String>, selectedOptions: List<String>, onSelectionChanged: (List<String>) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Text(label)
        options.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedOptions.contains(option),
                    onCheckedChange = {
                        val newList = if (it) selectedOptions + option else selectedOptions - option
                        onSelectionChanged(newList)
                    }
                )
                Text(option)
            }
        }
    }
} 