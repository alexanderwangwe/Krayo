package com.krayo.art.ui.screens.discover

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.krayo.art.MainActivity
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.components.content_display.CommentBar
import com.krayo.art.ui.components.content_display.ContentDisplay
import com.krayo.art.ui.components.content_display.ContentInfoData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DiscoverScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    updateNavState: (Boolean) -> Unit,
    contextActivity: MainActivity
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var hideContent by remember {
        mutableStateOf(false)
    }
    val categoriesListState = rememberLazyListState()

    val categories = arrayListOf(
        "For you",
        "Art",
        "Photography",
        "Music",
        "Dance",
        "Theatre",
        "Comedy",
        "Film",
        "Literature"
    )
    var selectedCategory by remember { mutableStateOf(categories[0]) }
    val contentDisplayMode = remember { mutableStateOf(false) }

    if (!contentDisplayMode.value) {
        val window = contextActivity.window
        window.navigationBarColor = Color.Transparent.toArgb()
        window.statusBarColor = Color.Transparent.toArgb()
        updateNavState(true)
    } else {
        updateNavState(false)
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            if (!contentDisplayMode.value)
                Column {
                    TopBar(
                        modifier = Modifier,
                        navController = navController
                    )
                    Categories(
                        items = categories,
                        updateSelected = { selectedCategory = it },
                        selected = selectedCategory,
                        state = categoriesListState
                    )
                }
            else
                if (!hideContent)
                    CenterAlignedTopAppBar(
                        navigationIcon = {
                            Surface(
                                onClick = {
                                    contentDisplayMode.value = false
                                },
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(35.dp),
                                color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.back),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(10.dp)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        title = {
                            Text(
                                text = selectedCategory,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )
                        })
        },
        bottomBar = {
            if (contentDisplayMode.value)
                CommentBar(context = contextActivity)
        },
    ) { paddingValues_ ->
        TrendingGrid(
            paddingValues = paddingValues_,
            navController = navController,
            contentInfoData = ContentInfoData(
                R.drawable.content,
                R.string.profile,
                "@krayo",
                "Krayo is a platform for artists to share their work with the world. #new #art #artist #ke. This can be a pretty long description though so that is something to keep in mind"
            ),
            hideContent = hideContent,
            updateHideState = {
                hideContent = !hideContent
            },
            updateCommentModalSheetState = {
                scope.launch {
                    if (sheetState.isVisible) {
                        sheetState.hide()
                    } else {
                        sheetState.show()
                    }
                }
            },
            contentDisplayMode = contentDisplayMode,
            updateContentDisplayMode = { contentDisplayMode.value = it }
        )
    }
}

private enum class TopBarState {
    DISCOVER,
    EVENTS
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun TopBar(
    modifier: Modifier,
    navController: NavController
) {
    val topBarState = remember { mutableStateOf(TopBarState.DISCOVER) }
    val textColorTrending =
        if (topBarState.value == TopBarState.DISCOVER) MaterialTheme.colorScheme.onSurface else Color.Gray
    val textColorEvents =
        if (topBarState.value == TopBarState.EVENTS) MaterialTheme.colorScheme.onSurface else Color.Gray
    val fontStyleTrending =
        if (topBarState.value == TopBarState.DISCOVER) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyMedium
    val fontStyleEvents =
        if (topBarState.value == TopBarState.EVENTS) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyMedium
    CenterAlignedTopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                color = textColorTrending,
                text = "Discover",
                style = fontStyleTrending,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .clickable {
                        topBarState.value = TopBarState.DISCOVER
                    }
            )
            Text(
                text = "Events",
                color = textColorEvents,
                style = fontStyleEvents,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .clickable {
                        topBarState.value = TopBarState.EVENTS
                    }
            )
        }
    }, actions = {
        Surface(
            shape = CircleShape,
            modifier = Modifier
                .size(35.dp),
            color = MaterialTheme.colorScheme.background,
            onClick = {
                navController.navigate(Destinations.CONTENT_SEARCH.name)
            }
        ) {
            Icon(
                Icons.Outlined.Search,
                modifier = Modifier.padding(7.5.dp),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(
    items: ArrayList<String>,
    updateSelected: (String) -> Unit,
    selected: String,
    state: LazyListState,
) {

    LazyRow(
        state = state,
    ) {

        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
        items(items.size) { index ->
            FilterChip(
                colors = FilterChipDefaults.filterChipColors(
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    containerColor = MaterialTheme.colorScheme.surface,
                    disabledSelectedContainerColor = MaterialTheme.colorScheme.surface,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.padding(horizontal = 5.dp),
                onClick = { updateSelected(items[index]) },
                label = {
                    Text(
                        items[index],
                        color = if (selected == items[index]) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                selected = selected == items[index],
                leadingIcon = if (selected == items[index]) {
                    {
                        Icon(
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
        }
        item {
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun TrendingAppBar(
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.padding(15.dp)
    ) {
        Text(
            text = "Trending",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(
            text = "Events",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun NothingTrending() {
    Column(
        modifier = Modifier.padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_camera),
            contentDescription = null
        )
        Text(
            text = "That's weird",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            fontSize = 25.sp,
        )

        Text(
            "Looks like nothing is trending right",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Text(
            text = "now"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrendingGrid(
    paddingValues: PaddingValues,
    navController: NavController,
    contentInfoData: ContentInfoData,
    hideContent: Boolean,
    updateHideState: () -> Unit,
    updateCommentModalSheetState: () -> Unit,
    contentDisplayMode: MutableState<Boolean>,
    updateContentDisplayMode: (Boolean) -> Unit,
) {
    val contentSize = 500
    val pagerState = rememberPagerState(pageCount = {
        500
    })
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyGridState()

    AnimatedContent(
        contentDisplayMode.value,
        label = "Animated Content"
    ) { targetState ->
        when (targetState) {
            true -> {
                VerticalPager(state = pagerState) { page ->
                    ContentDisplay(
                        updateScrollState = {
                            scope.launch {
                                scrollState.scrollToItem(page)
                            }
                        },
                        navController = navController,
                        contentInfoData = contentInfoData,
                        hideContent = hideContent,
                        updateHideState = updateHideState,
                        updateCommentModalSheetState = updateCommentModalSheetState,
                        updateContentDisplayMode = { updateContentDisplayMode(false) },
                        paddingValues = paddingValues,
                        parentDestination = Destinations.DISCOVER
                    )
                }
            }

            false -> {
                LazyVerticalGrid(
                    state = scrollState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding() + 10.dp)
                        .padding(horizontal = 15.dp),
                    columns = GridCells.Fixed(2),
                    content = {
                        items(count = contentSize) { i ->
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .aspectRatio(0.6f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.onBackground)
                                    .clickable {
                                        updateContentDisplayMode(true)
                                        scope.launch {
                                            pagerState.scrollToPage(i)
                                        }
                                    }
                            )
                        }
                    }
                )

            }
        }
    }

    Spacer(modifier = Modifier.height(25.dp))

}
