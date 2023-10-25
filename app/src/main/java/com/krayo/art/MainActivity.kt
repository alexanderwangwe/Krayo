package com.krayo.art

import SearchResultsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.screens.analytics.AnalyticsScreen
import com.krayo.art.ui.screens.authentication.AuthenticationScreen
import com.krayo.art.ui.screens.authentication.subscreens.AuthSuccessScreen
import com.krayo.art.ui.screens.authentication.subscreens.EmailVerification
import com.krayo.art.ui.screens.communities.CommunitiesScreen
import com.krayo.art.ui.screens.communities.subscreens.RequestsScreen
import com.krayo.art.ui.screens.content_creation.ContentCreationScreen
import com.krayo.art.ui.screens.content_search.ContentSearchScreen
import com.krayo.art.ui.screens.dashboard.DashboardScreen
import com.krayo.art.ui.screens.discover.DiscoverScreen
import com.krayo.art.ui.screens.home.HomeScreen
import com.krayo.art.ui.screens.product_creation.ProductCreationScreen
import com.krayo.art.ui.screens.product_creation.subscreens.AddProductScreen
import com.krayo.art.ui.screens.profile.ProfileScreen
import com.krayo.art.ui.theme.KrayoTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            var showBottomNav by rememberSaveable { mutableStateOf(true) }

            KrayoTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surface,
                    bottomBar = {
                        AnimatedContent(
                            showBottomNav,
                            label = "Animated Content"
                        ) { targetState ->
                            when (targetState) {
                                true -> {
                                    BottomNavigationBar(
                                        updateNavState = { show ->
                                            showBottomNav = show
                                        },
                                        WindowInsets.navigationBars, navController = navController
                                    )
                                }

                                false -> {
                                    // Do nothing
                                }
                            }
                        }
                    },
                    contentWindowInsets = WindowInsets.statusBars
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.REQUESTS.name,

                        ) {
                        composable(route = Destinations.HOME.name) {
                            val context = LocalContext.current
                            HomeScreen(navController, innerPadding) { show ->
                                showBottomNav = show
                            }
                        }
                        composable(route = Destinations.DISCOVER.name) {
                            DiscoverScreen(navController, innerPadding)
                        }

                        // CONTENT CREATION ROUTES
                        composable(route = Destinations.CONTENT_CREATION.name) {
                            ContentCreationScreen(updateNavState = { show ->
                                showBottomNav = show
                            }, navController, innerPadding)
                        }

                        // PRODUCT CREATION ROUTES
                        composable(route = Destinations.PRODUCT_CREATION.name) {
                            ProductCreationScreen(navController, innerPadding)
                        }

                        composable(route = Destinations.ADD_PRODUCT.name) {
                            AddProductScreen(navController, innerPadding)
                        }

                        composable(route = Destinations.SEARCH_RESULTS.name) {
                            SearchResultsScreen(
                                navController,
                                innerPadding,
                                updateNavState = { show ->
                                    showBottomNav = show
                                },
                            )
                        }
                        composable(route = Destinations.CHAT.name) {
                            CommunitiesScreen(navController, innerPadding)
                        }
                        composable(route = Destinations.PROFILE.name) {
                            ProfileScreen(navController, innerPadding)
                        }
                        composable(route = Destinations.ANALYTICS.name) {
                            AnalyticsScreen(navController, innerPadding)
                        }
                        composable(route = Destinations.AUTHENTICATION.name) {
                            AuthenticationScreen(
                                navController, innerPadding,
                                updateNavState = { show ->
                                    showBottomNav = show
                                },
                            )
                        }
                        composable(route = Destinations.COMMUNITIES.name) {
                            CommunitiesScreen(navController, innerPadding)
                        }
                        composable(route = Destinations.DASHBOARD.name) {
                            DashboardScreen(navController, innerPadding){ show ->
                                showBottomNav = show
                            }
                        }
                        composable(route = Destinations.CONTENT_SEARCH.name) {
                            ContentSearchScreen(
                                navController,
                                innerPadding,
                                updateNavState = { show ->
                                    showBottomNav = show
                                })
                        }

                        // AUTHENTICATION ROUTES
                        composable(route = Destinations.ACCOUNT_CREATION.name) {
                            AuthenticationScreen(
                                navController, innerPadding,
                                updateNavState = { show ->
                                    showBottomNav = show
                                },
                            )
                        }
                        composable(route = Destinations.EMAIL_VERIFICATION.name) {
                            EmailVerification(
                                navController, innerPadding,
                            )
                        }
                        composable(route = Destinations.AUTH_SUCCESS.name) {
                            AuthSuccessScreen(
                                navController, innerPadding,
                            )

                        }
                        composable(route = Destinations.REQUESTS.name) {
                            RequestsScreen(navController, innerPadding)
                        }

                    }
                }

            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    updateNavState: (Boolean) -> Unit,
    windowInsets: WindowInsets, modifier: Modifier = Modifier, navController: NavController
) {
    var bottomNavState by rememberSaveable {
        mutableStateOf(
            navController.currentDestination?.route ?: Destinations.HOME.name
        )
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = Color.White,
        indicatorColor = MaterialTheme.colorScheme.background,
    )

    NavigationBar(
        modifier = modifier
            .padding(top = 5.dp)
            .height(
                50.dp + windowInsets
                    .asPaddingValues()
                    .calculateBottomPadding()
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            ),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 0.dp,
        windowInsets = windowInsets
    ) {
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any {
                it.route == Destinations.HOME.name || it.route == Destinations.CONTENT_SEARCH.name || it.route == Destinations.SEARCH_RESULTS.name
            } == true,
            onClick = {
                updateNavState(true)
                bottomNavState = Destinations.HOME.name
                navController.navigate(Destinations.HOME.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.hut),
                    contentDescription = stringResource(
                        id = R.string.home
                    )
                )
            },
            colors = colors
        )
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == Destinations.DISCOVER.name } == true,
            onClick = {
                updateNavState(true)
                bottomNavState = Destinations.DISCOVER.name
                navController.navigate(Destinations.DISCOVER.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.eye),
                    contentDescription = stringResource(
                        id = R.string.discover
                    )
                )
            }, colors = colors
        )
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == Destinations.CONTENT_CREATION.name } == true,
            onClick = {
                updateNavState(true)
                bottomNavState = Destinations.CONTENT_CREATION.name
                navController.navigate(Destinations.CONTENT_CREATION.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.plus_math),
                    contentDescription = stringResource(
                        id = R.string.add
                    )
                )
            }, colors = colors
        )
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == Destinations.CHAT.name } == true,
            onClick = {
                updateNavState(true)
                bottomNavState = Destinations.CHAT.name
                navController.navigate(Destinations.CHAT.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.speech_bubble),
                    contentDescription = stringResource(
                        id = R.string.chat
                    )
                )
            },
            colors = colors
        )
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any { it.route == Destinations.PROFILE.name } == true,
            onClick = {
                updateNavState(true)
                bottomNavState = Destinations.PROFILE.name
                navController.navigate(Destinations.PROFILE.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.customer),
                    contentDescription = stringResource(
                        id = R.string.profile
                    )
                )
            }, colors = colors
        )
    }
}
