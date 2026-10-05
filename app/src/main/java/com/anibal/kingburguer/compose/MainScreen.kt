package com.anibal.kingburguer.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anibal.kingburguer.R
import com.anibal.kingburguer.compose.coupon.CouponScreen
import com.anibal.kingburguer.compose.home.HomeScreen
import com.anibal.kingburguer.compose.product.ProductScreen
import com.anibal.kingburguer.compose.profile.ProfileScreen
import com.anibal.kingburguer.ui.theme.KingBurguerTheme
import com.anibal.kingburguer.viewmodels.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(factory = MainViewModel.foctory),
    onNavigationToLogin:() -> Unit
){
    val shouldQuit = viewModel.uiState.collectAsState().value
    if (shouldQuit) {
        viewModel.reset()
        onNavigationToLogin()
    }
    MainScreen(
        onLogoutClicked = {
            viewModel.logout()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onLogoutClicked:() -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    var titleTopBarId by remember { mutableStateOf(R.string.menu_home) }

    val navBackStackEntry  by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        if (currentRoute == Screen.HOME.route){
            titleTopBarId = R.string.menu_home
        }
    }
    Scaffold(
        modifier = modifier
                .fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
             //   modifier = Modifier
                  //  .padding(top = contentPadding.calculateTopPadding()),
                title = {
                    Text(
                        text = stringResource(titleTopBarId),
                        color = MaterialTheme.colorScheme.onPrimary
                        )
                },
                navigationIcon = {
                    Icon(
                        modifier = modifier
                            .size(72.dp),
                        painter = painterResource(R.drawable.logo),
                        contentDescription = stringResource(R.string.app_name),
                        tint = Color.Unspecified
                    )
                },
                actions = {
                    IconButton(onClick = {onLogoutClicked()}) {
                            Icon(
                                imageVector = Icons.Filled.PowerSettingsNew,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                },
//                backgroundColor = MaterialTheme.colorScheme.primary,
//                contentColor = MaterialTheme.colorScheme.onPrimary,

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            MainBottomNavigation(navController){ titleId ->
                titleTopBarId = titleId
            }
        }
    ) { contentPadding ->
        Column (
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
            HomeScreen(){ productId ->
                navController.navigate("${Screen.PRODUCT.route}/$productId")
            }
        }

        composable (Screen.COUPON.route){
            CouponScreen(
//                modifier = Modifier.padding(
//                    top = contentPadding.calculateTopPadding(),
//                    bottom = contentPadding.calculateBottomPadding()
//                )
            )
        }

        composable (Screen.PROFILE.route){
            ProfileScreen()
        }

        composable (
            route = "${Screen.PRODUCT.route}/{productId}",
            arguments = listOf(
                navArgument("productId"){type = NavType.IntType}
            )
        ){
            ProductScreen(
                modifier = Modifier,
                onBackClicked = { navController.popBackStack() })
        }
    }
}

@Composable
fun MainBottomNavigation(
    navController: NavHostController,
    onNavegationChanged: (Int) -> Unit
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

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navigationItems.forEach { item ->
            NavigationBarItem(
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
                        onNavegationChanged(item.title)
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
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenLigthPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = false){
        MainScreen(onLogoutClicked = {})
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenDarkPreview() {
    KingBurguerTheme (dynamicColor = false, darkTheme = true){
        MainScreen(onLogoutClicked = {})
    }
}