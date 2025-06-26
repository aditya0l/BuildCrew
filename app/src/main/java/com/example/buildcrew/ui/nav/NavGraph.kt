package com.example.buildcrew.ui.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.buildcrew.ui.screens.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object ProfileSetup : Screen("profileSetup")
    object Home : Screen("home")
    object ProjectCreate : Screen("projectCreate")
    object ProjectDetail : Screen("projectDetail/{projectId}") {
        fun createRoute(projectId: String) = "projectDetail/$projectId"
    }
    object Chat : Screen("chat/{projectId}") {
        fun createRoute(projectId: String) = "chat/$projectId"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Signup.route) { SignupScreen(navController) }
        composable(Screen.ProfileSetup.route) { ProfileSetupScreen(navController) }
        composable(Screen.Home.route) { HomeFeedScreen(navController) }
        composable(Screen.ProjectCreate.route) { ProjectCreateScreen(navController) }
        composable(Screen.ProjectDetail.route) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: ""
            ProjectDetailScreen(navController, projectId)
        }
        composable(Screen.Chat.route) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: ""
            ChatScreen(navController, projectId)
        }
    }
} 