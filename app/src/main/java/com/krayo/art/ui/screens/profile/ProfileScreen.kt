package com.krayo.art.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.krayo.art.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, paddingValues: PaddingValues) {
    var showMenu by remember { mutableStateOf(false) }
    var profileView by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent,
                ),
                navigationIcon = {
                    if(profileView){
                        Surface(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .clickable {

                                },
                            color = MaterialTheme.colorScheme.background,
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
                title = { Text("Profile") },
                actions = {
                    Surface(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable {

                            },
                        color = MaterialTheme.colorScheme.background,
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
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) { paddingValues_ ->
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
                    painter = painterResource(id = R.drawable.csm_header),
                    contentDescription = "Image",
                    contentScale = ContentScale.FillBounds,
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                                    MaterialTheme.colorScheme.surface
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
                ProfileHeader()
            }
        }

    }

}

@Composable
private fun ProfileHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.surface,
                    shape = CircleShape)
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
                        color = MaterialTheme.colorScheme.primary
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
                        color = MaterialTheme.colorScheme.primary
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
        PagingScreen()
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
        Image(
            modifier = Modifier.size(75.dp),
            painter = painterResource(id = R.drawable.no_camera),
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
fun PagingScreen() {
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
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else Color.Gray
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

