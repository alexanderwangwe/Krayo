package com.krayo.art

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.krayo.art.ui.screens.profile.ProfileScreen
import com.krayo.art.ui.screens.onboarding.OnboardingScreen
import com.krayo.art.ui.theme.KrayoTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
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
                            WindowInsets.navigationBars,
                            navController = navController
                        )
                    },
                    contentWindowInsets = WindowInsets.statusBars
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.ONBOARDING.name,

                        ) {
                        composable(route = Destinations.HOME.name) {
                            val context = LocalContext.current
                            HomeScreen(navController, innerPadding)
                        }
                        composable(route = Destinations.DISCOVER.name) {
                            DiscoverScreen()
                        }
                        composable(route = Destinations.CONTENT_CREATION.name) {
                            ContentCreationScreen()
                        }
                        composable(route = Destinations.CHAT.name) {
                            CommunitiesScreen()
                        }
                        composable(route = Destinations.PROFILE.name) {
                            ProfileScreen()
                        }
                        composable(route = Destinations.ANALYTICS.name) {
                            AnalyticsScreen()
                        }
                        composable(route = Destinations.AUTHENTICATION.name) {
                            AuthenticationScreen()
                        }
                        composable(route = Destinations.COMMUNITIES.name) {
                            CommunitiesScreen()
                        }
                        composable(route = Destinations.DASHBOARD.name) {
                            DashboardScreen()
                        }
                        composable(route = Destinations.CONTENT_SEARCH.name) {
                            ContentSearchScreen()
                        }
                        composable(route = Destinations.ONBOARDING.name) {
                            OnboardingScreen()
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun BottomNavigationBar(
    windowInsets: WindowInsets,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var bottomNavState by rememberSaveable {
        mutableStateOf(
            navController.currentDestination?.route ?: Destinations.HOME.name
        )
    }

    val contentColors = listOf(
        Color(0xBF181818),
        Color(0xFF000000)
    )
    val normalColors = listOf(
        MaterialTheme.colorScheme.surface,
        Color(0xFF000000)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = if (bottomNavState == Destinations.HOME.name) contentColors else normalColors
                )
            )
            .fillMaxWidth()
            .height(100.dp)
            .padding(windowInsets.asPaddingValues())

    ) {
        BottomNavigationItem(
            icon = R.drawable.outline_home_24,
            label = R.string.home,
            selected = bottomNavState == Destinations.HOME.name,
            onClick = {
                bottomNavState = Destinations.HOME.name
                navController.navigate(Destinations.HOME.name)
            }
        )
        BottomNavigationItem(
            icon = R.drawable.outline_remove_red_eye_24,
            label = R.string.discover,
            selected = bottomNavState == Destinations.DISCOVER.name,
            onClick = {
                bottomNavState = Destinations.DISCOVER.name
                navController.navigate(Destinations.DISCOVER.name)
            }
        )
        BottomNavigationItem(
            icon = R.drawable.baseline_add_24,
            label = R.string.add,
            selected = bottomNavState == Destinations.CONTENT_CREATION.name,
            onClick = {
                bottomNavState = Destinations.CONTENT_CREATION.name
                navController.navigate(Destinations.CONTENT_CREATION.name)
            }
        )
        BottomNavigationItem(
            icon = R.drawable.baseline_chat_bubble_outline_24,
            label = R.string.chat,
            selected = bottomNavState == Destinations.CHAT.name,
            onClick = {
                bottomNavState = Destinations.CHAT.name
                navController.navigate(Destinations.CHAT.name)
            }
        )
        BottomNavigationItem(
            icon = R.drawable.outline_person_24,
            label = R.string.profile,
            selected = bottomNavState == Destinations.PROFILE.name,
            onClick = {
                bottomNavState = Destinations.PROFILE.name
                navController.navigate(Destinations.PROFILE.name)
            }
        )
    }
}

@Composable
private fun BottomNavigationItem(
    modifier: Modifier = Modifier,
    icon: Int,
    label: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    Icon(
        tint = if (selected) MaterialTheme.colorScheme.primary else Color.White,
        modifier = modifier.clickable { onClick() },
        painter = painterResource(id = icon),
        contentDescription = stringResource(label)
    )
}
