package com.sayeed_dev.notiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sayeed_dev.notiz.data.SessionManager
import com.sayeed_dev.notiz.ui.screen.AddNoteScreen
import com.sayeed_dev.notiz.ui.screen.HomeScreen
import com.sayeed_dev.notiz.ui.screen.LoginScreen
import com.sayeed_dev.notiz.ui.screen.SignUpScreen
import com.sayeed_dev.notiz.ui.screen.WelcomeScreen
import com.sayeed_dev.notiz.ui.viewmodel.NoteViewModel

// Proti ta screen ar akta kre unique address
sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object AddNote : Screen("add_note")
}

@Composable
fun NotizNavGraph(isLoggedIn: Boolean) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    
    // 🚀 ASOL BRAIN: NoteViewModel load kora holo puro app-er jonno
    val noteViewModel: NoteViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Screen.Home.route else Screen.Welcome.route
    ) {
        // 1. Welcome Screen
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) }
            )
        }

        // 2. Login Screen
        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    sessionManager.setLoginStatus(true)
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) }
            )
        }

        // 3. Sign Up Screen
        composable(route = Screen.SignUp.route) {
            SignUpScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        // 4. Home Screen (Connected with real logic)
        composable(route = Screen.Home.route) {
            HomeScreen(
                onAddNoteClick = { navController.navigate(Screen.AddNote.route) },
                onNoteClick = { note ->
                    // 🚀 শুধু আইডি পাঠানো হচ্ছে
                    navController.navigate("edit_note/${note.id}")
                },
                viewModel = noteViewModel
            )
        }

        // 5. Add Note Screen (Real save logic)
        composable(route = Screen.AddNote.route) {
            AddNoteScreen(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { title, content, isPinned ->
                    if (title.isNotEmpty() || content.isNotEmpty()) {
                        noteViewModel.saveNote(title = title, content = content, isPinned = isPinned)
                    }
                    navController.popBackStack()
                }
            )
        }

        // 6. Edit Note Screen (ID ভিত্তিক নেভিগেশন)
        composable(
            route = "edit_note/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            AddNoteScreen(
                initialId = id,
                onBackClick = { navController.popBackStack() },
                onSaveClick = { newTitle, newContent, newIsPinned ->
                    noteViewModel.saveNote(id = id, title = newTitle, content = newContent, isPinned = newIsPinned)
                    navController.popBackStack()
                },
                onDeleteClick = {
                    noteViewModel.deleteNoteById(id)
                    navController.popBackStack()
                }
            )
        }
    }
}
