package com.anibal.kingburguer.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anibal.kingburguer.compose.home.HomeScreen
import com.anibal.kingburguer.compose.login.LoginScreen

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
            LoginScreen(navController)
        }
        composable(Screen.HOME.route) {
            HomeScreen(navController)
        }
    }

}
