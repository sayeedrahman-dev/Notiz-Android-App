package com.sayeed_dev.notiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sayeed_dev.notiz.data.SessionManager
import com.sayeed_dev.notiz.ui.screen.AddNoteScreen
import com.sayeed_dev.notiz.ui.screen.HomeScreen
import com.sayeed_dev.notiz.ui.screen.LoginScreen
import com.sayeed_dev.notiz.ui.screen.SignUpScreen
import com.sayeed_dev.notiz.ui.screen.WelcomeScreen
import kotlin.math.round

//Proti ta screen ar akta kre unique address
sealed class Screen(val route: String){
    object Welcome: Screen("Welcome")
    object Login: Screen("Login")
    object SignUp: Screen("SignUp")
    object Home: Screen("Home")
    object AddNote : Screen("add note")
}

@Composable
fun NotizNavGraph(isLoggedIn: Boolean){
    val navController = rememberNavController()
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Screen.Home.route else Screen.Welcome.route
    ){
        composable(route = Screen.Welcome.route){
            WelcomeScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route)},
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route)}
            )
        }
        composable(route = Screen.Login.route){
            LoginScreen(
                onNavigateToHome = {
                    sessionManager.setLoginStatus(true)
                    navController.navigate(Screen.Home.route){
                        popUpTo(navController.graph.id){
                            inclusive = true
                        }
                    }
                },
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route)}
            )
        }
        composable(route = Screen.SignUp.route){
            SignUpScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route)}
            )
        }
        composable(route = Screen.Home.route){
            HomeScreen(
                onAddNoteClick = { navController.navigate(Screen.AddNote.route)}
            )
        }
        composable(route = Screen.AddNote.route){
            AddNoteScreen(
                onBackClick = {navController.popBackStack()},
                onSaveClick = {title, content ->
                    navController.popBackStack()
                }
            )
        }
    }
}