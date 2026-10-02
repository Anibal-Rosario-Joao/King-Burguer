package com.anibal.kingburguer.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anibal.kingburguer.compose.login.LogInScreen
import com.anibal.kingburguer.compose.signup.SignUpScreen
import com.anibal.kingburguer.viewmodels.SplashViewModel

@Composable
fun KingBurguerApp(
    startDestination: Screen
) {
    val navController = rememberNavController()
    KingBurguerNavHost(navController, startDestination)
}

@Composable
fun KingBurguerNavHost(
    navController: NavHostController,
    startDestination: Screen
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
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
        composable(Screen.SIGNUP.route){
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
