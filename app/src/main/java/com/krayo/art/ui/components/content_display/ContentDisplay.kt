package com.krayo.art.ui.components.content_display

import android.app.Activity
import android.media.MediaCodec
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.interactors.GlobalFunctions
import com.krayo.art.ui.screens.home.Comments
import com.krayo.art.ui.theme.DeepRed
import com.krayo.art.ui.theme.Purple20
import com.krayo.art.ui.theme.fontFamily
import com.krayo.art.ui.theme.fontFamilyBold
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ContentDisplay(
    navController: NavController,
    contentInfoData: ContentInfoData,
    hideContent: Boolean,
    updateHideState: () -> Unit,
    updateCommentModalSheetState: () -> Unit,
    updateContentDisplayMode: () -> Unit,
    paddingValues: PaddingValues,
    parentDestination: Destinations
) {
    val context = LocalView.current.context
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            Comments(navController = navController, paddingValues = paddingValues)
        },
    ) {
        BackHandler {
            updateContentDisplayMode()
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .combinedClickable(
                    onClick = {
                        //TODO: Pause video
                    },
                    onLongClick = {
                        updateHideState()
                    },
                )
                .offset(y = -paddingValues.calculateTopPadding() + 12.5.dp),
            color = Purple20
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
                            scope.launch {
                                if (sheetState.isVisible) {
                                    sheetState.hide()
                                } else {
                                    sheetState.show()
                                    //updateShowNavState(false)
                                }
                            }
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentBar(
    modifier: Modifier = Modifier,
    context: Activity
) {
    var comment = remember {
        mutableStateOf("")
    }
    val interactionSource = remember { MutableInteractionSource() }

    val window = context.window
    window.navigationBarColor = Color.Black.toArgb()
    window.statusBarColor = Color.Transparent.toArgb()
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = WindowInsets.navigationBars
                    .asPaddingValues()
                    .calculateBottomPadding()
            ).shadow(elevation = 4.dp, ambientColor = Color.White),
        color = Color.Black,
    ) {
        BasicTextField(
            value = comment.value,
            onValueChange = { comment.value = it },
            visualTransformation = VisualTransformation.None,
            modifier = modifier.padding(vertical = 10.dp),
            interactionSource = interactionSource,
            enabled = true,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
            ),
            maxLines = 5,
            singleLine = false,
        ) { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                leadingIcon = {
                    Icon(
                        tint = Color.White,
                        painter = painterResource(id = R.drawable.speech_bubble),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(Icons.Outlined.Send, tint = Color.White, contentDescription = null)
                },
                placeholder = {
                    Text(
                        "Comment...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color.Black,
                    textColor = Color.White,
                ),
                value = comment.value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = true,
                enabled = true,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(horizontal = 15.dp, vertical = 0.dp),
            )
        }
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
fun ContentInfo(
    modifier: Modifier = Modifier,
    contentInfoData: ContentInfoData,
    navController: NavController
) {
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
                Box(
                    modifier = modifier
                        .size(30.dp)
                        .background(Color.White, CircleShape)
                ) {
                    Icon(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(5.dp)
                            .clickable {
                                // TODO: Add functionality
                            },
                        painter = painterResource(id = R.drawable.plus_math),
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
            Spacer(
                modifier =
                Modifier.width(10.dp)
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ), onClick = {
                    navController.navigate(Destinations.ORDER_CHECKOUT.name)
                }) {
                Text("Buy", style = MaterialTheme.typography.bodyLarge)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow {
            for (text in description) {
                if (text[0] == '#') {
                    Text(
                        text = "$text ",
                        fontFamily = fontFamilyBold,
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
                    fontFamily = fontFamilyBold,
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
                    fontFamily = fontFamilyBold,
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

    IconButton(onClick = {
        onClick()
    }) {
        Icon(
            modifier = modifier
                .padding(15.dp)
                .width(30.dp)
                .height(30.dp),
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = label),
            tint = if (type == Interactions.BOOKMARK) bookmarkCondition else likeCondition
        )
    }
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
            icon = R.drawable.heart,
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
            icon = R.drawable.speech_bubble2,
            label = R.string.comment,
            selected = selected == Interactions.COMMENT,
            type = Interactions.COMMENT,
            onClick = {
                selected = Interactions.COMMENT
                onClick(Interactions.COMMENT)
            })
        InteractionItem(
            icon = R.drawable.add_bookmark,
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
            icon = R.drawable.share,
            label = R.string.share,
            selected = selected == Interactions.SHARE,
            type = Interactions.SHARE,
            onClick = {
                selected = Interactions.SHARE
                onClick(Interactions.SHARE)
            })
    }
}

data class VideoUiState(
    val video: Uri,
    val id: Int,
    val name: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoPlayer(
    uri: Uri,
    modifier: Modifier,
    playedFromNonHomeOrDiscover: Boolean = false,
    playWhenReadyParent: Boolean,
    updateHideState: () -> Unit = {},
) {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle

    val exoPlayer by remember {
        mutableStateOf<SimpleExoPlayer?>(
            SimpleExoPlayer.Builder(context).build().apply {
                try {
                    setMediaItem(MediaItem.fromUri(uri))
                    repeatMode = Player.REPEAT_MODE_ALL
                    prepare()
                    playWhenReady = playWhenReady
                } catch (e: Exception) {
                    Log.d("VideoPlayer", "VideoPlayer: ${e.message}")
                }
            }

        )
    }

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    exoPlayer?.play()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer?.pause()
                }

                else -> {
                    // Ignore all other events
                }
            }
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(
        playWhenReadyParent
    ) {
        if (playWhenReadyParent) {
            exoPlayer?.play()
        } else {
            exoPlayer?.pause()
        }
    }

    var isPlaying by remember { mutableStateOf(exoPlayer?.isPlaying) }
    var duration by remember { mutableLongStateOf(0L) }
    var currentPosition by remember { mutableLongStateOf(0L) }

    LaunchedEffect(exoPlayer) {
        while (isActive) {
            delay(50) // Update every 50 milli second
            currentPosition = exoPlayer?.currentPosition ?: 0L
            duration = if (exoPlayer?.duration!! > 0) exoPlayer?.duration ?: 0L else 0L
        }
    }

    // Do not recreate the player everytime this Composable commits

    val listener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying_: Boolean) {
            isPlaying = isPlaying_
        }
    }

    val listener1 = object : Player.Listener {
        override fun onPlaybackStateChanged(state: Int) {
            when (state) {
                Player.STATE_BUFFERING, Player.STATE_READY -> {
                    isPlaying = exoPlayer?.isPlaying
                }

                Player.STATE_ENDED -> {
                    // DO nun
                }

                Player.STATE_IDLE -> {
                    // DO nun
                }
            }
        }
    }

    val listener2 = object : Player.Listener {
        override fun onPlayerError(error: PlaybackException) {
            val cause = error.cause
            Log.d("VideoPlayer", "onPlayerError: ${error.message}")
            if (cause is HttpDataSource.HttpDataSourceException) {
                // An HTTP error occurred.
                val httpError = cause
                // It's possible to find out more about the error both by casting and by querying
                // the cause.
                if (httpError is HttpDataSource.InvalidResponseCodeException) {
                    // Cast to InvalidResponseCodeException and retrieve the response code, message
                    // and headers.
                } else {
                    // Try calling httpError.getCause() to retrieve the underlying cause, although
                    // note that it may be null.
                }
            }
        }
    }

    DisposableEffect(
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(
                    onClick = {
                        if (isPlaying == true) {
                            exoPlayer?.pause()
                        } else {
                            exoPlayer?.play()
                        }
                    },
                    onLongClick = {
                        updateHideState()
                    },
                )
                .clickable {
                    if (isPlaying == true) {
                        exoPlayer?.pause()
                    } else {
                        exoPlayer?.play()
                    }
                },
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    setPadding(0, 0, 0, 0)
                }
            },
        )
    ) {

        exoPlayer?.addListener(listener)
        exoPlayer?.addListener(listener1)
        exoPlayer?.addListener(listener2)
        onDispose {
            exoPlayer?.removeListener(listener)
            exoPlayer?.removeListener(listener1)
            exoPlayer?.removeListener(listener2)
            exoPlayer?.pause()
            exoPlayer?.release()
        }
    }

    LaunchedEffect(exoPlayer) {
        snapshotFlow { exoPlayer?.isPlaying }
            .collect { isPlaying = it }
    }

    LaunchedEffect(exoPlayer) {
        snapshotFlow { exoPlayer?.duration }
            .collect {
                if (it != null) {
                    if (it > 0) {
                        duration = it.toLong()
                    }
                }
            }
    }

    exoPlayer?.videoScalingMode = MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        if(!playedFromNonHomeOrDiscover)
            IconButton(
                onClick = {
                if (isPlaying == true) {
                    exoPlayer?.pause()
                } else {
                    exoPlayer?.play()
                }
            }) {
                Icon(
                    if (isPlaying == true) painterResource(id = R.drawable.baseline_pause_24) else painterResource(
                        id = R.drawable.baseline_play_arrow_24
                    ),
                    tint = Color.White,
                    contentDescription = if (isPlaying == true) "Pause" else "Play"
                )
            }

        Slider(
            modifier = if(playedFromNonHomeOrDiscover) Modifier.padding(bottom = 25.dp).weight(1f) else Modifier.weight(1f),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Purple20,
                inactiveTrackColor = Color.White
            ),
            value = currentPosition.toFloat(),
            onValueChange = { value ->
                exoPlayer?.seekTo(value.toLong())
            },
            valueRange = 0f..duration.toFloat(),
        )
    }
}
