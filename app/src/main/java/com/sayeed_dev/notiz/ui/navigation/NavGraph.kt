package com.sayeed_dev.notiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sayeed_dev.notiz.ui.screen.EmptyHomeScreen
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

}

@Composable
fun NotizNavGraph(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
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
                    navController.navigate(Screen.Home.route){
                        popUpTo(Screen.Login.route){ inclusive = true}
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
            EmptyHomeScreen()
        }
    }
}