package com.anibal.kingburguer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.anibal.kingburguer.compose.KingBurguerApp
import com.anibal.kingburguer.compose.signup.SignUpScreen
import com.anibal.kingburguer.ui.theme.KingBurguerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KingBurguerTheme(dynamicColor = false) {
                KingBurguerApp()
            }
        }
    }
}



