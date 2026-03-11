package com.anibal.kingburguer.compose.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.anibal.kingburguer.component.KingButton
import com.anibal.kingburguer.component.KingTextTitle
import com.anibal.kingburguer.compose.Screen
import com.anibal.kingburguer.compose.login.LoginScreen
import com.anibal.kingburguer.ui.theme.KingBurguerTheme
import com.anibal.kingburguer.viewmodels.LoginViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

       // val uiState by viewModel.uiState.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top)
            ) {

                Text(
                    text = "Home Screen".uppercase(),
                    fontSize = 34.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )
                KingButton(
                    text = "Go to Login"
                ) {
                   // viewModel.reset()
                   // navController.navigate(Screen.LOGIN.route)

                }
            }

        }
    }
}

@Preview
@Composable
fun HomeScreenLigthPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = false){
        HomeScreen(rememberNavController())
    }
}
@Preview
@Composable
fun HomeScreenDarkPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = true){
        HomeScreen(rememberNavController())
    }
}