package com.krayo.art

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.screens.analytics.AnalyticsScreen
import com.krayo.art.ui.screens.authentication.AuthenticationScreen
import com.krayo.art.ui.screens.communities.CommunitiesScreen
import com.krayo.art.ui.screens.content_creation.ContentCreationScreen
import com.krayo.art.ui.screens.content_search.ContentSearchScreen
import com.krayo.art.ui.screens.dashboard.DashboardScreen
import com.krayo.art.ui.screens.discover.DiscoverScreen
import com.krayo.art.ui.screens.home.HomeScreen
import com.krayo.art.ui.screens.onboarding.FirstOnboardingScreen
import com.krayo.art.ui.screens.profile.ProfileScreen
import com.krayo.art.ui.screens.onboarding.OnboardingScreen
import com.krayo.art.ui.theme.KrayoTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()

            KrayoTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surface,
                    bottomBar = {
                        BottomNavigationBar(
                            WindowInsets.navigationBars, navController = navController
                        )
                    },
                    contentWindowInsets = WindowInsets.statusBars
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.ONBOARDINGPROCESS.name,

                        ) {
                        composable(route = Destinations.HOME.name) {
                            val context = LocalContext.current
                            HomeScreen(navController, innerPadding)
                        }
                        composable(route = Destinations.DISCOVER.name) {
                            DiscoverScreen(navController, innerPadding)
                        }
                        composable(route = Destinations.CONTENT_CREATION.name) {
                            ContentCreationScreen(navController, innerPadding)
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
                            AuthenticationScreen(navController, innerPadding)
                        }
                        composable(route = Destinations.COMMUNITIES.name) {
                            CommunitiesScreen(navController, innerPadding)
                        }
                        composable(route = Destinations.DASHBOARD.name) {
                            DashboardScreen(navController, innerPadding)
                        }
                        composable(route = Destinations.CONTENT_SEARCH.name) {
                            ContentSearchScreen(navController, innerPadding)
                        }
                        composable(route = Destinations.ONBOARDING.name) {
                            OnboardingScreen(navController, innerPadding)
                        }
                        composable(route = Destinations.ONBOARDINGPROCESS.name) {
                            FirstOnboardingScreen(
                                navController = navController, innerPadding
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    windowInsets: WindowInsets, modifier: Modifier = Modifier, navController: NavController
) {
    var bottomNavState by rememberSaveable {
        mutableStateOf(
            navController.currentDestination?.route ?: Destinations.HOME.name
        )
    }

    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = Color.White,
        indicatorColor = MaterialTheme.colorScheme.background,
    )
    NavigationBar(
        modifier = modifier.height(75.dp).background(brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.secondary
            )
        )),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 0.dp,
        windowInsets = windowInsets
    ) {
        NavigationBarItem(selected = bottomNavState == Destinations.HOME.name, onClick = {
            bottomNavState = Destinations.HOME.name
            navController.navigate(Destinations.HOME.name)
        }, icon = {
            Icon(
                painter = painterResource(id = R.drawable.outline_home_24),
                contentDescription = stringResource(
                    id = R.string.home
                )
            )
        }, colors = colors)
        NavigationBarItem(selected = bottomNavState == Destinations.DISCOVER.name, onClick = {
            bottomNavState = Destinations.DISCOVER.name
            navController.navigate(Destinations.DISCOVER.name)
        }, icon = {
            Icon(
                painter = painterResource(id = R.drawable.outline_remove_red_eye_24),
                contentDescription = stringResource(
                    id = R.string.discover
                )
            )
        }, colors = colors)
        NavigationBarItem(
            selected = bottomNavState == Destinations.CONTENT_CREATION.name,
            onClick = {
                bottomNavState = Destinations.CONTENT_CREATION.name
                navController.navigate(Destinations.CONTENT_CREATION.name)
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = stringResource(
                        id = R.string.add
                    )
                )
            }, colors = colors
        )
        NavigationBarItem(selected = bottomNavState == Destinations.CHAT.name, onClick = {
            bottomNavState = Destinations.CHAT.name
            navController.navigate(Destinations.CHAT.name)
        }, icon = {
            Icon(
                painter = painterResource(R.drawable.baseline_chat_bubble_outline_24),
                contentDescription = stringResource(
                    id = R.string.chat
                )
            )
        }, colors = colors)
        NavigationBarItem(selected = bottomNavState == Destinations.PROFILE.name, onClick = {
            bottomNavState = Destinations.PROFILE.name
            navController.navigate(Destinations.PROFILE.name)
        }, icon = {
            Icon(
                painter = painterResource(id = R.drawable.outline_person_24),
                contentDescription = stringResource(
                    id = R.string.profile
                )
            )
        }, colors = colors)
    }
}
