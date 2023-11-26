package com.krayo.art

import SearchResultsScreen
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
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
import com.krayo.art.ui.screens.content_creation.subscreens.ContentCreationOptionsScreen
import com.krayo.art.ui.screens.content_creation.subscreens.LastCapturedContent
import com.krayo.art.ui.screens.content_search.ContentSearchScreen
import com.krayo.art.ui.screens.dashboard.DashboardScreen
import com.krayo.art.ui.screens.dashboard.subscreens.DispatchSuccessScreen
import com.krayo.art.ui.screens.dashboard.subscreens.OrderViewScreen
import com.krayo.art.ui.screens.discover.DiscoverScreen
import com.krayo.art.ui.screens.home.HomeScreen
import com.krayo.art.ui.screens.onboarding.FirstOnboardingScreen
import com.krayo.art.ui.screens.onboarding.OnboardingBegin
import com.krayo.art.ui.screens.onboarding.OnboardingScreen
import com.krayo.art.ui.screens.order_checkout.OrderCheckoutScreen
import com.krayo.art.ui.screens.payment_methods.CreditOrDebitScreen
import com.krayo.art.ui.screens.product_creation.ProductCreationScreen
import com.krayo.art.ui.screens.product_creation.subscreens.AddProductScreen
import com.krayo.art.ui.screens.profile.NavDrawerItem
import com.krayo.art.ui.screens.profile.ProfileScreen
import com.krayo.art.ui.theme.Grey80
import com.krayo.art.ui.theme.KrayoTheme
import com.krayo.art.ui.theme.LightGrey
import kotlinx.coroutines.launch
import java.io.File


class MainActivity : ComponentActivity() {
    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    private enum class DrawerItem {
        GIVE_FEEDBACK,
        REPORT_BUG,
        SUGGEST_FEATURE
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            var showBottomNav by rememberSaveable { mutableStateOf(true) }
            var drawerState = rememberDrawerState(DrawerValue.Closed)
            val drawerIsOpen = drawerState.currentValue == DrawerValue.Closed
            val scope = rememberCoroutineScope()
            var selected by rememberSaveable {
                mutableStateOf(DrawerItem.GIVE_FEEDBACK)
            }

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
                                        windowInsets = WindowInsets.navigationBars,
                                        navController = navController,
                                        context = this@MainActivity
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

                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        ModalNavigationDrawer(
                            drawerState = drawerState,
                            gesturesEnabled = true,
                            drawerContent = {
                                ModalDrawerSheet(
                                    drawerContainerColor = MaterialTheme.colorScheme.surface,
                                    modifier = Modifier
                                        .fillMaxWidth(0.75f)
                                        .background(MaterialTheme.colorScheme.surface)
                                        .clip(RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(horizontal = 15.dp)
                                            .padding(bottom = 15.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.End
                                            ) {
                                                Icon(
                                                    modifier = Modifier
                                                        .padding(top = 15.dp)
                                                        .size(35.dp)
                                                        .clickable {
                                                            scope.launch {
                                                                drawerState.close()
                                                            }
                                                        },
                                                    painter = painterResource(id = R.drawable.cancel),
                                                    contentDescription = stringResource(
                                                        id = R.string.cancel
                                                    )
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(15.dp))
                                            NavDrawerItem(
                                                name = "Give Feedback",
                                                selected = DrawerItem.GIVE_FEEDBACK == selected
                                            ) {
                                                selected = DrawerItem.GIVE_FEEDBACK
                                            }
                                            NavDrawerItem(
                                                name = "Report a bug",
                                                selected = DrawerItem.REPORT_BUG == selected
                                            ) {
                                                selected = DrawerItem.REPORT_BUG
                                            }
                                            NavDrawerItem(
                                                name = "Suggest a feature",
                                                selected = DrawerItem.SUGGEST_FEATURE == selected
                                            ) {
                                                selected = DrawerItem.SUGGEST_FEATURE
                                            }
                                        }
                                    }
                                }
                            }
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = Destinations.HOME.name,

                                ) {
                                composable(route = Destinations.HOME.name) {
                                    val context = LocalContext.current
                                    HomeScreen(navController, innerPadding) { show ->
                                        showBottomNav = show
                                    }
                                }
                                composable(route = Destinations.DISCOVER.name) {
                                    DiscoverScreen(navController, innerPadding, updateNavState = { show ->
                                        showBottomNav = show
                                    }, this@MainActivity)
                                }

                                // CONTENT CREATION ROUTES
                                composable(route = Destinations.CONTENT_CREATION.name) {
                                    ContentCreationScreen(
                                        updateNavState = { show ->
                                            showBottomNav = show
                                        },
                                        navController = navController,
                                        paddingValues = innerPadding,
                                        context = this@MainActivity
                                    ) {
                                        getOutputDirectory()
                                    }
                                }
                                composable(route = Destinations.CONTENT_CREATION_OPTIONS.name) {
                                    ContentCreationOptionsScreen(navController, innerPadding)
                                }

                                // PRODUCT CREATION ROUTES
                                composable(route = Destinations.PRODUCT_CREATION.name) {
                                    ProductCreationScreen(navController, innerPadding)
                                }

                                composable(route = Destinations.ADD_PRODUCT.name) {
                                    AddProductScreen(navController, innerPadding)
                                }

                        composable(route = Destinations.REQUESTS.name) {
                            RequestsScreen(navController, innerPadding)
                        }
                                composable(route = Destinations.SEARCH_RESULTS.name) {
                                    SearchResultsScreen(
                                        navController,
                                        innerPadding,
                                        context = this@MainActivity,
                                        updateNavState = { show ->
                                            showBottomNav = show
                                        },
                                    )
                                }
                                composable(route = Destinations.CHAT.name) {
                                    CommunitiesScreen(navController, innerPadding)
                                }
                                composable(route = Destinations.PROFILE.name) {
                                    ProfileScreen(
                                        navController,
                                        innerPadding,
                                        updateNavState = { show ->
                                            showBottomNav = show
                                        })
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


                                // Dashboard routes
                                composable(route = Destinations.DASHBOARD.name) {
                                    DashboardScreen(navController, innerPadding) { show ->
                                        showBottomNav = show
                                    }
                                }
                                composable(route = Destinations.ORDER_VIEW.name) {
                                    OrderViewScreen(navController, innerPadding) { show ->
                                        showBottomNav = show
                                    }
                                }
                                composable(route = Destinations.DISPATCH_SUCCESS.name) {
                                    DispatchSuccessScreen(navController, innerPadding) { show ->
                                        showBottomNav = show
                                    }
                                }

                                // Content creation routes
                                composable(route = Destinations.CONTENT_SEARCH.name) {
                                    ContentSearchScreen(
                                        navController,
                                        innerPadding,
                                        updateNavState = { show ->
                                            showBottomNav = show
                                        })
                                }

                                composable(route = Destinations.ONBOARDING.name) {
                                    OnboardingScreen(
                                        navController,
                                        innerPadding,
                                        context = this@MainActivity,
                                        updateNavState = { show ->
                                            showBottomNav = show
                                        })
                                }
                                composable(route = Destinations.ONBOARDING_PROCESS.name) {
                                    FirstOnboardingScreen(navController = navController,
                                        innerPadding,
                                        context = this@MainActivity,
                                        updateNavState = { show ->
                                            showBottomNav = show
                                        })
                                }
                                composable(route = Destinations.ONBOARDING_BEGIN.name) {
                                    OnboardingBegin(
                                        navController = navController,
                                        innerPadding,
                                        context = this@MainActivity
                                    )
                                }

                                composable(route = Destinations.LAST_CAPTURED_CONTENT.name) {
                                    LastCapturedContent(
                                        navController = navController,
                                        activity = this@MainActivity
                                    )
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

                                // ORDER CHECKOUT ROUTES
                                composable(route = Destinations.ORDER_CHECKOUT.name) {
                                    OrderCheckoutScreen(
                                        innerPadding, navController,
                                        context = this@MainActivity,
                                        updateNavState = {
                                            showBottomNav = it
                                        }
                                    )
                                }
                                composable(route = Destinations.CREDIT_OR_DEBIT_CARD.name) {
                                    CreditOrDebitScreen(
                                        innerPadding, navController,
                                    ) {
                                        showBottomNav
                                    }

                                }
                            }
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
    navController: NavController,
    context: MainActivity,
    updateNavState: (Boolean) -> Unit,
) {
    var bottomNavState by rememberSaveable {
        mutableStateOf(
            navController.currentDestination?.route ?: Destinations.HOME.name
        )
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val colorsHome = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = Color.White,
        indicatorColor = MaterialTheme.colorScheme.surface,
    )

    val colorsNormal = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.primary,
        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
        indicatorColor = MaterialTheme.colorScheme.surface,
    )

    val colors = if (currentDestination?.hierarchy?.any { it.route == Destinations.HOME.name } == true) colorsHome else colorsNormal
    val color = MaterialTheme.colorScheme.onBackground

    val window = (context as Activity).window
    if(navController.graph.findStartDestination().id == currentDestination?.id){
        window.navigationBarColor = Color.Black.toArgb()
        window.statusBarColor = Color.Transparent.toArgb()
    }else{
        window.navigationBarColor = Color.Transparent.toArgb()
        window.statusBarColor = Color.Transparent.toArgb()
    }

    if(currentDestination?.hierarchy?.any { it.route == Destinations.PROFILE.name } == true) {
        window.statusBarColor = Color.Transparent.toArgb()
    }

    NavigationBar(
        modifier = modifier
            .height(
                55.dp + windowInsets
                    .asPaddingValues()
                    .calculateBottomPadding()
            )
            .drawBehind {
                val strokeWidth = 1f
                val x = size.width - strokeWidth
                val y = size.height - strokeWidth

                //top line
                drawLine(
                    color = color, start = Offset(0f, 0f), //(0,0) at top-left point of the box
                    end = Offset(x, 0f), //top-right point of the box
                    strokeWidth = strokeWidth
                )
            },
        containerColor = if (currentDestination?.hierarchy?.any { it.route == Destinations.HOME.name } == true) Color.Black else MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        windowInsets = windowInsets
    ) {
        NavigationBarItem(
            selected = currentDestination?.hierarchy?.any {
                it.route == Destinations.HOME.name
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
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.homevector),
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
        NavigationBarItem(selected = currentDestination?.hierarchy?.any { it.route == Destinations.ONBOARDING_PROCESS.name } == true,
            onClick = {
                if (onBoardingIsCompleted(context = context)) {
                    updateNavState(false)
                    bottomNavState = Destinations.ONBOARDING.name
                    navController.popBackStack(Destinations.ONBOARDING.name, false)
                } else {
                    updateNavState(true)
                    bottomNavState = Destinations.ONBOARDING_PROCESS.name
                    navController.navigate(Destinations.ONBOARDING_PROCESS.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
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
            selected = currentDestination?.hierarchy?.any { it.route == Destinations.PROFILE.name || it.route == Destinations.DASHBOARD.name } == true,
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

private fun onBoardingIsCompleted(context : MainActivity):Boolean{
    val sharedPreferences = context.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isCompleted", false)
}
