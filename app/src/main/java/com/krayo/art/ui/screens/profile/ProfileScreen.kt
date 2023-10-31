package com.krayo.art.ui.screens.profile

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.interactors.PalleteColorGenerator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    updateNavState: (Boolean) -> Unit
) {
    updateNavState(true)
    var showMenu by remember { mutableStateOf(false) }
    var profileView by remember { mutableStateOf(false) }
    var colors by rememberSaveable { mutableStateOf(emptyMap<String, String>()) }
    val imageDrawable = R.drawable.content
    val context = LocalContext.current
    val bitmap = PalleteColorGenerator.convertImageToBitmap(
        image = imageDrawable,
        context = context
    )
    colors = PalleteColorGenerator.extractColorsFromBitmap(bitmap = bitmap!!)

    var drawerState = rememberDrawerState(DrawerValue.Closed)
    val drawerIsOpen = drawerState.currentValue == DrawerValue.Closed
    val scope = rememberCoroutineScope()
    var artist by remember { mutableStateOf(true) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AnimatedContent(
                drawerIsOpen,
                label = "Animated Profile Bar Content"
            ) { targetState ->
                when (targetState) {
                    true -> {
                        ProfileTopAppBar(
                            scrollBehavior = scrollBehavior,
                            profileView = profileView,
                            scope = scope,
                            drawerState = drawerState,
                            scrolledColor = colors["vibrant"]?.let {
                                Color(it.toColorInt())
                            } ?: MaterialTheme.colorScheme.secondary
                        )
                    }

                    false -> {
                        // Do nothing
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) { paddingValues_ ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                gesturesEnabled = true,
                drawerContent = {
                    ProfileDrawerContent(
                        isAnArtist = artist,
                        scope = scope,
                        paddingValues = paddingValues_,
                        paddingValuesParent = paddingValues,
                        drawerState = drawerState,
                        navController = navController
                    )
                }
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        ) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = painterResource(id = R.drawable.content),
                                contentDescription = "Image",
                                contentScale = ContentScale.FillBounds,
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                colors["darkVibrant"]?.let {
                                                    Color(it.toColorInt()).copy(
                                                        0.5f
                                                    )
                                                }
                                                    ?: MaterialTheme.colorScheme.secondary,
                                                Color.Black
                                            )
                                        )
                                    )
                                    .fillMaxSize(),
                            ) {}
                        }

                        Column(
                            modifier = Modifier
                                .padding(15.dp)
                                .offset(y = (-50).dp)
                        ) {
                            ProfileHeader(
                                following = false, // TODO: Make this dynamic
                                profileView = profileView,
                                color = colors["vibrant"]
                            )
                            PagingScreen(
                                color = Color(colors["vibrant"]!!.toColorInt())
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    profileView: Boolean,
    scope: CoroutineScope,
    drawerState: DrawerState,
    scrolledColor: Color
) {
    val padding = if (profileView) 10.dp else 0.dp
    TopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = scrolledColor
        ),
        navigationIcon = {
            if (profileView) {
                Surface(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable {

                        },
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                    shape = CircleShape
                ) {
                    Icon(
                        painterResource(id = R.drawable.back),
                        modifier = Modifier.padding(10.dp),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(
                            id = R.string.go_back
                        )
                    )
                }
            }
        },
        title = {
            Box(modifier = Modifier.padding(padding)) {
                Text("Profile", color = Color.White)
            }
        },
        actions = {
            if (!profileView)
                Surface(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                    shape = CircleShape
                ) {
                    Icon(
                        Icons.Outlined.Menu,
                        modifier = Modifier.padding(10.dp),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(
                            id = R.string.add_product
                        )
                    )
                }

        })
}

private enum class DrawerItem {
    DASHBOARD,
    EDIT_PROFILE,
    SETTINGS,
    ANALYTICS,
    LOGOUT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileDrawerContent(
    isAnArtist: Boolean,
    paddingValues: PaddingValues,
    paddingValuesParent: PaddingValues,
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavController
) {
    var selected by rememberSaveable {
        mutableStateOf(DrawerItem.DASHBOARD)
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        ModalDrawerSheet(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(
                    bottom = paddingValuesParent.calculateBottomPadding()
                )
        ) {
            Column(modifier = Modifier.padding(horizontal = 15.dp).padding(bottom = 15.dp)) {
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
                    if (isAnArtist) {
                        NavDrawerItem(
                            name = "Dashboard",
                            selected = DrawerItem.DASHBOARD == selected
                        ) {
                            selected = DrawerItem.DASHBOARD
                            navController.navigate(Destinations.DASHBOARD.name)
                        }
                    }
                    NavDrawerItem(
                        name = "Edit profile",
                        selected = DrawerItem.EDIT_PROFILE == selected
                    ) {
                        selected = DrawerItem.EDIT_PROFILE
                        // TODO: navigate to the edit profile screen
                    }

                    if (isAnArtist) {
                        NavDrawerItem(
                            name = "Analytics",
                            selected = DrawerItem.ANALYTICS == selected
                        ) {
                            selected = DrawerItem.ANALYTICS
                            //navController.navigate(Destinations.ANALYTICS.name)
                        }
                    }
                    NavDrawerItem(name = "Settings", selected = DrawerItem.SETTINGS == selected) {
                        selected = DrawerItem.SETTINGS
                        // TODO: navigate to the settings screen
                    }
                }
                NavDrawerItem(
                    icon = R.drawable.outline_logout_25,
                    name = "Logout",
                    guidingColor = Color.Black,
                    unselectedContainerColor = MaterialTheme.colorScheme.tertiary,
                    selected = DrawerItem.LOGOUT == selected
                ) {
                    selected = DrawerItem.LOGOUT
                    // TODO: navigate to the edit profile screen
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawerItem(
    name: String,
    selected: Boolean,
    unselectedContainerColor: Color = MaterialTheme.colorScheme.background,
    guidingColor: Color = MaterialTheme.colorScheme.onSurface,
    @DrawableRes icon: Int = R.drawable.chevron_right_25,
    onClick: () -> Unit,
) {
    val color = if (selected) Color.Black else guidingColor
    NavigationDrawerItem(
        modifier = Modifier.padding(top = 15.dp),
        onClick = {
            onClick()
        },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.onSurface,
            unselectedContainerColor = unselectedContainerColor,
            unselectedIconColor = MaterialTheme.colorScheme.onSurface,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
        ),
        icon = {
            Icon(
                painterResource(id = icon),
                tint = color,
                contentDescription = name
            )
        },
        label = {
            Text(name, color = color)
        }, selected = selected
    )
}

@Composable
private fun ProfileHeader(
    profileView: Boolean,
    color: String?,
    following: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    )
            ) {
                Image(
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    painter = painterResource(id = R.drawable.insert_photo),
                    contentDescription = "Image",
                    contentScale = ContentScale.FillBounds,
                )
            }
            if (profileView)
                Surface(
                    color = Color(color!!.toColorInt()),
                    modifier = modifier
                        .height(35.dp)
                        .width(if (following) 100.dp else 35.dp)
                        .clip(CircleShape)
                        .border(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.surface,
                            shape = CircleShape
                        )
                        .align(Alignment.BottomEnd)
                ) {
                    if (following)
                        Text(
                            "unfollow",
                            textAlign = TextAlign.Center,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(5.dp)
                        )
                    else
                        Icon(
                            modifier = modifier.padding(5.dp),
                            painter = painterResource(id = R.drawable.plus_math),
                            contentDescription = null
                        )
                }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column {
            Column {
                Text(
                    text = "Some Artist",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "@someartist",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row {
                Row{
                    Text(
                        text = "123",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 13.sp,
                        color = Color(color!!.toColorInt())
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "followers",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Row{
                    Text(
                        text = "13",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 13.sp,
                        color = Color(color!!.toColorInt())
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "following",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun NoItems() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Outlined.Clear,
            modifier = Modifier.size(75.dp),
            contentDescription = stringResource(
                id = R.string.no_item_image
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Uh Oh",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            textAlign = TextAlign.Center,
            text = "Looks like you don't have anything in your collections",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

data class TabItem(
    val text: String,
    val screen: @Composable () -> Unit
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagingScreen(
    color: Color
) {
    val coroutineScope = rememberCoroutineScope()

    val tabRowItems = listOf(
        TabItem(
            text = "Posts",
            screen = { NoItems() }
        ),
        TabItem(
            text = "Collections",
            screen = { NoItems() }
        ),
        TabItem(
            text = "Likes",
            screen = { NoItems() }
        )
    )

    val pagerState = rememberPagerState(
        pageCount = tabRowItems.size,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            containerColor = Color.Transparent,
            divider = {
                Divider(
                    color = MaterialTheme.colorScheme.background,
                    thickness = 1.dp,
                )
            },
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = color
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            selectedTabIndex = pagerState.currentPage
        ) {
            tabRowItems.forEachIndexed { index, item ->
                Tab(
                    text = {
                        Text(
                            text = item.text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (pagerState.currentPage == index) color else Color.Gray
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
        ) {
            tabRowItems[pagerState.currentPage].screen()
        }
    }
}

