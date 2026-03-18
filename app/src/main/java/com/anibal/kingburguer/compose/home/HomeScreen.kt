package com.anibal.kingburguer.compose.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.anibal.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun HomeScreen(
    navController: NavHostController,
   // viewModel: HomeViewModel = viewModel()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
        ) {
            HomeContentScreen(
             //   viewModel = viewModel,
                navController = navController
            )
        }
    }
}

@Composable
private fun HomeContentScreen(
  //  viewModel : HomeViewModel ,
    navController: NavHostController
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen".uppercase())
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenLigthPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = false){
        HomeScreen(rememberNavController())
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenDarkPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = true){
        HomeScreen(rememberNavController())
    }
}