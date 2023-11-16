import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.MainActivity
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.components.content_display.CommentBar
import com.krayo.art.ui.components.content_display.ContentDisplay
import com.krayo.art.ui.components.content_display.ContentInfoData
import com.krayo.art.ui.screens.content_search.components.SearchTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SearchResultsScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    updateNavState: (Boolean) -> Unit,
    context: MainActivity
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var hideContent by remember {
        mutableStateOf(false)
    }
    val searchTerm = "Contemporary Art On a bench in the pack"
    val contentDisplayMode = remember { mutableStateOf(false) }

    if (!contentDisplayMode.value) {
        val window = context.window
        window.navigationBarColor = Color.Transparent.toArgb()
        window.statusBarColor = Color.Transparent.toArgb()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!contentDisplayMode.value)
                SearchTopBar(
                    paddingValues = WindowInsets.statusBars.asPaddingValues(),
                    navController = navController,
                    defaultValue = "Contemporary Art"
                )
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
                                    .size(40.dp),
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
                                maxLines = 1,
                                modifier = Modifier.width(200.dp),
                                overflow = TextOverflow.Ellipsis,
                                text = searchTerm,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )
                        })
        },
        bottomBar = {
            if (contentDisplayMode.value)
                CommentBar(context = context)

        }
    ) { paddingValuesScaffold ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchResults(
                paddingValues = paddingValuesScaffold,
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
}

@Composable
private fun SearchResultsItem() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(0.6f)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.onBackground)
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun SearchResults(
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
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    AnimatedContent(
        contentDisplayMode.value,
        label = "Animated Content"
    ) { targetState ->
        when (targetState) {
            true -> {
                VerticalPager(
                    state = pagerState
                ) { page ->
                    ContentDisplay(
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
}
