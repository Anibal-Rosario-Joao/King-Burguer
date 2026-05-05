package com.anibal.kingburguer.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anibal.kingburguer.compose.home.MainScreen
import com.anibal.kingburguer.compose.login.LogInScreen
import com.anibal.kingburguer.compose.signup.SignUpScreen

@Composable
fun KingBurguerApp() {
    val navController = rememberNavController()
    KingBurguerNavHost(navController = navController)


}

@Composable
fun KingBurguerNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.LOGIN.route
    ) {
        composable(Screen.LOGIN.route) {
            LogInScreen(
                navController = navController,
                onNavigateToHome = {
                    navController.navigate(Screen.MAIN.route){
                        popUpTo(Screen.LOGIN.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.SIGNUP.route) {
            SignUpScreen(
                navController = navController,
                onNavigationClick = {
                    navController.navigateUp()
                },
                onNavigateToLogin = {
                    navController.navigateUp()
                })
        }
        composable (Screen.MAIN.route){
            MainScreen()
        }
    }

}
