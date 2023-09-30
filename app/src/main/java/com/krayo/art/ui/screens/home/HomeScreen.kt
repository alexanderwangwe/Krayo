package com.krayo.art.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.interactors.GlobalFunctions
import com.krayo.art.ui.theme.DeepRed
import com.krayo.art.ui.theme.fontFamily
import com.krayo.art.ui.theme.fontFamilyBold
import com.krayo.art.ui.theme.fontFamilyLight
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeScreen(navController: NavController, paddingValues: PaddingValues) {
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
        val pagerState = rememberPagerState(pageCount = {
            10
        })
        val scope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        ModalBottomSheetLayout(
            sheetBackgroundColor = MaterialTheme.colorScheme.surface,
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            sheetContent = {
                Comments(navController = navController, paddingValues = paddingValues)
            },
        ) {
            VerticalPager(state = pagerState) { page ->
                Content(
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
            .padding(15.dp)
            .clickable {
                navController.navigate(Destinations.PROFILE.name)
            },
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
                    text = comment.name,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
                Text(
                    text = comment.comment,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
                Row(
                    modifier = modifier.padding(top = 5.dp),
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_favorite_24),
                        contentDescription = stringResource(id = R.string.like),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                            .clickable {
                                // Handle like
                            }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_insert_comment_24),
                        contentDescription = stringResource(id = R.string.comment),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 15.dp).size(20.dp)
                            .clickable {
                                // Handle add reply
                            }
                    )
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
    val count = 7
    Column(modifier = modifier.zIndex(10f)){
        Text(
            text = "Comments",
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontFamily = fontFamilyBold,
            ),
            color = MaterialTheme.colorScheme.onPrimary,
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

@Composable
fun Content(
    hideContent: Boolean,
    modifier: Modifier = Modifier,
    contentInfoData: ContentInfoData,
    updateHideState: () -> Unit = {},
    updateCommentModalSheetState: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val context = LocalView.current.context

    Surface(
        modifier = modifier
            .height(screenHeight)
            .clickable {
                updateHideState()
            },
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier
                .absolutePadding(bottom = 15.dp)
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
                )
            )
            Interactions { it ->
                when (it) {
                    Interactions.LIKE -> {
                        // Update DB
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
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .alpha(if (hideContent) 0f else 1f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = R.drawable.outline_notifications_active_24),
            contentDescription = stringResource(id = R.string.search),
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .clickable {
                    navController.navigate(Destinations.CONTENT_SEARCH.name)
                }
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.weight(1f)
        ) {
            val condition = preference == Preference.FOR_YOU
            val condition2 = preference == Preference.FOLLOWING
            Column {
                Text(
                    text = stringResource(id = R.string.for_you),
                    style = TextStyle(
                        shadow = Shadow(
                            color = if (condition) Color.Black else Color.Transparent,
                            blurRadius = 1f
                        ),
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontFamily = if (condition) fontFamilyBold else fontFamilyLight,
                    ),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .clickable {
                            preference = Preference.FOR_YOU
                        }
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.resource__),
                contentDescription = stringResource(id = R.string.search),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(10.dp)
            )
            Text(
                text = stringResource(id = R.string.following),
                style = TextStyle(
                    shadow = Shadow(
                        color = if (condition2) Color.Black else Color.Transparent, blurRadius = 1f
                    ),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontFamily = if (condition2) fontFamilyBold else fontFamilyLight,
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .clickable {
                        preference = Preference.FOLLOWING
                    }
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.outline_search_24),
            contentDescription = stringResource(id = R.string.search),
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .clickable {
                    navController.navigate(Destinations.CONTENT_SEARCH.name)
                }
        )
    }
}

@Composable
fun InteractionItem(
    icon: Int,
    label: Int,
    selected: Boolean,
    type: Interactions,
    onClick: () -> Unit,
    liked: Boolean = false,
    bookmarked: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val likeCondition = if (liked) DeepRed else Color.White
    val bookmarkCondition = if (bookmarked) MaterialTheme.colorScheme.secondary else Color.White
    Icon(
        modifier = modifier
            .padding(15.dp)
            .width(30.dp)
            .height(30.dp)
            .clickable {
                onClick()
            },
        painter = painterResource(id = icon),
        contentDescription = stringResource(id = label),
        tint = if (type == Interactions.BOOKMARK) bookmarkCondition else likeCondition
    )
}

enum class Interactions {
    LIKE,
    COMMENT,
    BOOKMARK,
    SHARE,
    FULLSCREEN,
    UNSELECTED
}

@Composable
fun Interactions(
    modifier: Modifier = Modifier,
    onClick: (Interactions) -> Unit
) {
    var liked by remember {
        mutableStateOf(false)
    }
    var bookmarked by remember {
        mutableStateOf(false)
    }
    var selected by remember {
        mutableStateOf(Interactions.UNSELECTED)
    }
    Column(
        modifier = modifier
            .absolutePadding(bottom = 25.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        InteractionItem(
            icon = R.drawable.baseline_favorite_24,
            label = R.string.like,
            selected = selected == Interactions.LIKE,
            liked = liked,
            type = Interactions.LIKE,
            onClick = {
                onClick(Interactions.LIKE)
                selected = Interactions.LIKE
                liked = !liked
            })
        InteractionItem(
            icon = R.drawable.baseline_insert_comment_24,
            label = R.string.comment,
            selected = selected == Interactions.COMMENT,
            type = Interactions.COMMENT,
            onClick = {
                selected = Interactions.COMMENT
                onClick(Interactions.COMMENT)
            })
        InteractionItem(
            icon = R.drawable.baseline_bookmark_add_24,
            label = R.string.bookmark,
            selected = selected == Interactions.BOOKMARK,
            bookmarked = bookmarked,
            type = Interactions.BOOKMARK,
            onClick = {
                selected = Interactions.BOOKMARK
                onClick(Interactions.BOOKMARK)
                bookmarked = !bookmarked
            })
        InteractionItem(
            icon = R.drawable.baseline_ios_share_24,
            label = R.string.share,
            selected = selected == Interactions.SHARE,
            type = Interactions.SHARE,
            onClick = {
                selected = Interactions.SHARE
                onClick(Interactions.SHARE)
            })
    }
}

data class ContentInfoData(
    val image: Int,
    val label: Int,
    val name: String,
    val description: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContentInfo(modifier: Modifier = Modifier, contentInfoData: ContentInfoData) {
    val descriptionArray = contentInfoData.description.split(" ")
    var description by remember {
        mutableStateOf(descriptionArray.subList(0, 20))
    }
    var showMore by rememberSaveable {
        mutableStateOf(descriptionArray.size > 20)
    }
    Column(
        modifier = modifier.padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box() {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.content),
                    contentDescription = contentInfoData.name
                )
                Surface(
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape)
                        .align(Alignment.BottomEnd),
                    color = Color.White
                ) {
                    Icon(
                        modifier = modifier
                            .padding(15.dp)
                            .width(20.dp)
                            .height(20.dp)
                            .clickable {
                                // TODO: Add functionality
                            },
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = stringResource(id = R.string.add),
                        tint = Color.Black
                    )
                }
            }
            Spacer(
                modifier =
                Modifier.width(10.dp)
            )
            Text(
                text = contentInfoData.name,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow {
            for (text in description) {
                if (text[0] == '#') {
                    Text(
                        text = "$text ",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                } else {
                    Text(
                        style = MaterialTheme.typography.bodySmall,
                        text = "$text ",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    )
                }
            }
            if (showMore) {
                Text(
                    text = "...show more",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    modifier = Modifier.clickable {
                        description = contentInfoData.description.split(" ")
                        showMore = false
                    }
                )
            } else {
                Text(
                    text = "show less",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    modifier = Modifier.clickable {
                        description = contentInfoData.description.split(" ").subList(0, 20)
                        showMore = true
                    }
                )
            }
        }

    }
}
