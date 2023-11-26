package com.krayo.art.ui.screens.home

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.interactors.GlobalFunctions
import com.krayo.art.ui.components.content_display.ContentInfo
import com.krayo.art.ui.components.content_display.ContentInfoData
import com.krayo.art.ui.components.content_display.Interactions
import com.krayo.art.ui.components.content_display.VideoPlayer
import com.krayo.art.ui.theme.Purple20
import com.krayo.art.ui.theme.fontFamily
import com.krayo.art.ui.theme.fontFamilyBold
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    updateShowNavState: (Boolean) -> Unit = {}) {
    var hideContent by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(bottom = paddingValues.calculateBottomPadding()),
        topBar = {
            TopBar(
                navController = navController,
                paddingValues = paddingValues,
                hideContent = hideContent
            )
        }
    ) { padding ->
        val context = LocalView.current.context
        val videos = arrayListOf<Uri>(
            Uri.parse("android.resource://${context.packageName}/${R.raw.content2}"),
            Uri.parse("android.resource://${context.packageName}/${R.raw.content}"),
        )
        val pagerState = rememberPagerState(pageCount = {
            videos.size
        })
        val scope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        if (sheetState.currentValue != ModalBottomSheetValue.Hidden) {
            updateShowNavState(false)
        }else{
            updateShowNavState(true)
        }

        ModalBottomSheetLayout(
            sheetBackgroundColor = MaterialTheme.colorScheme.surface,
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            sheetContent = {
                Comments(navController = navController, paddingValues = paddingValues)
            },
        ) {
            VerticalPager(state = pagerState) { page ->
                var isVisible by remember { mutableStateOf(false) }
                val screenHeightDp = LocalConfiguration.current.screenHeightDp

                val modifier = Modifier.fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        isVisible = coordinates.boundsInParent().top <= screenHeightDp
                    }

                Content(
                    updateShowNavState = {
                        updateShowNavState(it)
                    },
                    hideContent = hideContent,
                    updateHideState = {
                        hideContent = !hideContent
                    },
                    navController = navController,
                    updateCommentModalSheetState = {
                        scope.launch {
                            if (sheetState.isVisible) {
                                sheetState.hide()
                            } else {
                                sheetState.show()
                                //updateShowNavState(false)
                            }
                        }
                    },
                    isVisible = isVisible,
                    modifier = modifier,
                    page = page,
                    currentPage = pagerState.currentPage,
                    videoURI = videos[page],
                    contentInfoData = ContentInfoData(
                        R.drawable.content,
                        R.string.profile,
                        "@krayo",
                        "Krayo is a platform for artists to share their work with the world. #new #art #artist #ke. This can be a pretty long description though so that is something to keep in mind"
                    ),
                )
            }
        }
    }
}

data class Comment(
    val image: Int,
    val name: String,
    val comment: String
)

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    navController: NavController,
    comment: Comment
) {
    Row(
        modifier = modifier
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row() {
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.content),
                contentDescription = "@krayo"
            )
            Spacer(
                modifier =
                Modifier.width(10.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = comment.name,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = comment.comment,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.bodySmall,
                )
                Row(
                    modifier = modifier.padding(top = 5.dp),
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.heart),
                        contentDescription = stringResource(id = R.string.like),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                // Handle like
                            }
                    )
                    Spacer(Modifier.width(10.dp))
                    Text("reply", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun Comments(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val count = 14255
    Column(modifier = modifier
        .zIndex(10f)
        .fillMaxHeight(0.75f)){
        Text(
            text = "Comments - $count",
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontFamily = fontFamilyBold,
            ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(15.dp)
        )
        LazyColumn {
            items(count) {
                Comment(
                    navController = navController,
                    comment = Comment(
                        R.drawable.content,
                        "@krayo",
                        "This is a comment, with a pretty big ammount of text on it so yeah"
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Content(
    hideContent: Boolean,
    modifier: Modifier = Modifier,
    contentInfoData: ContentInfoData,
    navController: NavController,
    updateShowNavState: (Boolean) -> Unit = {},
    updateHideState: () -> Unit = {},
    updateCommentModalSheetState: () -> Unit = {},
    isVisible: Boolean,
    page: Int,
    videoURI: Uri,
    currentPage: Int,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val context = LocalView.current.context

    Surface(
        modifier = Modifier
            .height(screenHeight),
        color = Purple20
    ) {
        VideoPlayer(videoURI, modifier, playWhenReadyParent = page == currentPage, updateHideState = updateHideState)
        Row(
            modifier = Modifier
                .absolutePadding(bottom = 35.dp)
                .alpha(if (hideContent) 0f else 1f),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {

            ContentInfo(
                modifier = Modifier.weight(1f),
                contentInfoData = ContentInfoData(
                    R.drawable.content,
                    R.string.profile,
                    contentInfoData.name,
                    contentInfoData.description
                ),
                navController = navController
            )
            Interactions { it ->
                when (it) {
                    Interactions.LIKE -> {
                        // Update DB
                        navController.navigate(Destinations.AUTHENTICATION.name)
                    }

                    Interactions.COMMENT -> {
                        updateCommentModalSheetState()
                    }

                    Interactions.BOOKMARK -> {
                        // Update DB

                    }

                    Interactions.SHARE -> {
                        GlobalFunctions().ShareSheetMessage(
                            "Check out this awesome content on Krayo!"
                        ).let { intent ->
                            context.startActivity(intent)
                        }
                    }

                    else -> {

                    }
                }
            }
        }
    }
}

enum class Preference {
    LIVE,
    FOR_YOU,
    FOLLOWING
}

@Composable
fun TopBar(
    hideContent: Boolean,
    modifier: Modifier = Modifier,
    navController: NavController,
    paddingValues: PaddingValues
) {
    var preference by remember {
        mutableStateOf(Preference.FOR_YOU)
    }

    Row(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .alpha(if (hideContent) 0f else 1f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = {
            navController.navigate(Destinations.CONTENT_SEARCH.name)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.alarm),
                contentDescription = stringResource(id = R.string.search),
                tint = Color.White,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.weight(1f)
        ) {
            val condition = preference == Preference.FOR_YOU
            val condition2 = preference == Preference.FOLLOWING
            val condition3 = preference == Preference.LIVE

            Surface(
                modifier = Modifier.weight(1f),
                color = if (condition3) Color.White.copy(0.75f) else Color.Transparent,
                shape = RoundedCornerShape(35.dp)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.market),
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            blurRadius = 1f
                        ),
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontFamily = fontFamilyBold,
                    ),
                    color = if (condition3) Color.Black else Color.White,
                    modifier = Modifier
                        .clickable {
                            preference = Preference.LIVE
                        }
                        .padding(vertical = 5.dp)
                )
            }
            Surface(
                modifier = Modifier.weight(1f),
                color = if (condition) Color.White.copy(0.75f) else Color.Transparent,
                shape = RoundedCornerShape(35.dp)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.for_you),
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            blurRadius = 1f
                        ),
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontFamily = fontFamilyBold,
                    ),
                    color = if (condition) Color.Black else Color.White,
                    modifier = Modifier
                        .clickable {
                            preference = Preference.FOR_YOU
                        }
                        .padding(vertical = 5.dp)
                )
            }
            Surface(
                modifier = Modifier.weight(1f),
                color = if (condition2) Color.White.copy(0.75f) else Color.Transparent,
                shape = RoundedCornerShape(35.dp)
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.following),
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            blurRadius = 1f
                        ),
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontFamily = fontFamilyBold,
                    ),
                    color = if (condition2) Color.Black else Color.White,
                    modifier = Modifier
                        .clickable {
                            preference = Preference.FOLLOWING
                        }
                        .padding(vertical = 5.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        IconButton(onClick = {
            navController.navigate(Destinations.CONTENT_SEARCH.name)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = stringResource(id = R.string.search),
                tint = Color.White,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
        }
    }
}
