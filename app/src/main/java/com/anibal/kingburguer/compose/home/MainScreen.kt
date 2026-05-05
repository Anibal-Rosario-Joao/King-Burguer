package com.anibal.kingburguer.compose.home

import android.media.Image
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.anibal.kingburguer.R
import com.anibal.kingburguer.compose.Screen
import com.anibal.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun MainScreen(
    //navController: NavHostController,
   // viewModel: HomeViewModel = viewModel()
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.background,
        topBar = {},
        bottomBar = {
            MainBottomNavigation(navController)
        }
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .wrapContentSize()
                .padding(contentPadding)
        ) {
            MainContentScreen(navController,contentPadding)
            }
        }

}

data class NavigationItem(
    @StringRes val title: Int,
    val icon: ImageVector,
    val router: Screen
)

@Composable
fun MainContentScreen(
    navController: NavHostController,
    contentPadding: PaddingValues
){
    NavHost(
        navController = navController,
        startDestination = Screen.HOME.route
    ) {
        composable (Screen.HOME.route){
            HomeScreen(
                modifier = Modifier.padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                )
            )
        }

        composable (Screen.COUPON.route){
            CouponScreen(
                modifier = Modifier.padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                )
            )
        }

        composable (Screen.PROFILE.route){
            ProfileScreen(
                modifier = Modifier.padding(
                    top = contentPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                )
            )
        }
    }
}

@Composable
fun MainBottomNavigation(
    navController: NavHostController
){
    val navigationItems = listOf(
        NavigationItem(
            title = R.string.menu_home,
            icon = Icons.Default.Home,
            router = Screen.HOME
        ),
        NavigationItem(
            title = R.string.menu_coupon,
            icon = Icons.Default.ShoppingCart,
            router = Screen.COUPON
        ),
        NavigationItem(
            title = R.string.menu_profile,
            icon = Icons.Default.Person,
            router = Screen.PROFILE
        )
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        val navBackStackEntry  by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navigationItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.router.route,
                onClick = {
                    if(currentRoute != item.router.route) {
                        navController.navigate(item.router.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(item.title)
                    )
                },
                label = {
                    Text(stringResource(item.title))
                },
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                selectedContentColor = MaterialTheme.colorScheme.primary

            )
        }
    }
}
@Composable
private fun HomeScreen(
    modifier: Modifier
){
    Text(
        text = "Home Screen".uppercase(),
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
private fun CouponScreen(
    modifier: Modifier
){
    Text(
        text = "Coupon Screen".uppercase(),
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
private fun ProfileScreen(
    modifier: Modifier
){
    Text(
    text = "Profile Screen".uppercase(),
    style = MaterialTheme.typography.headlineLarge
)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenLigthPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = false){
        MainScreen()
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenDarkPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = true){
        MainScreen()
    }
}