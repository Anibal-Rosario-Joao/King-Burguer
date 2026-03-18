package com.anibal.kingburguer.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anibal.kingburguer.compose.home.HomeScreen
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
                    navController.navigate(Screen.HOME.route){
                        popUpTo(Screen.LOGIN.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.SIGUP.route) {
            SignUpScreen(
                navController = navController,
                onNavigationClick = {
                    navController.navigateUp()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.HOME.route){
                        popUpTo(Screen.LOGIN.route){
                            inclusive = true
                        }
                    }
                }
                )
        }
        composable (Screen.HOME.route){
            HomeScreen(navController = navController)
        }
    }

}
