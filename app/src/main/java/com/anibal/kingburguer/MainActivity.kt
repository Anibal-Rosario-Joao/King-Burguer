package com.anibal.kingburguer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.anibal.kingburguer.compose.KingBurguerApp
import com.anibal.kingburguer.compose.KingBurguerNavHost
import com.anibal.kingburguer.compose.Screen
import com.anibal.kingburguer.compose.signup.SignUpScreen
import com.anibal.kingburguer.ui.theme.KingBurguerTheme
import com.anibal.kingburguer.viewmodels.SplashViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels { SplashViewModel.factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            Log.d("MainActivity", "Mantenha ligado")
            viewModel.hasSession.value == null
        }
        enableEdgeToEdge()
        setContent {
            KingBurguerTheme(dynamicColor = false) {
                val startState = viewModel.hasSession.collectAsState(null)
                // null -> nao chamou = tela em branco
                // false -> deslogado
                // true -> logado
                Log.d("MainActivity", "Começou a composição! $startState")
                startState.value?.let { logged ->
                    val startDestination = if (logged) Screen.MAIN else Screen.LOGIN
                    KingBurguerApp(startDestination)
                }
            }
        }
    }
}



