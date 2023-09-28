package com.krayo.art.ui.screens.home

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
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations
import com.krayo.art.ui.theme.DeepRed
import com.krayo.art.ui.theme.fontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, paddingValues: PaddingValues) {
    Scaffold(
        topBar = {
            TopBar(navController = navController, paddingValues = paddingValues)
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                modifier = Modifier.absolutePadding(bottom = 125.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                ContentInfo(
                    modifier = Modifier.weight(1f),
                    contentInfoData = ContentInfoData(
                        R.drawable.content,
                        R.string.profile,
                        "@krayo",
                        "Krayo is a platform for artists to share their work with the world. #new #art #artist #ke. This can be a pretty long description though so that is something to keep in mind"
                    )
                )
                Interactions()
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
            .padding(horizontal = 15.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            tint = MaterialTheme.colorScheme.onSurface,
            painter = painterResource(id = R.drawable.outline_notifications_active_24),
            contentDescription = stringResource(id = R.string.notifications),
            modifier = Modifier
                .clickable {
                    //navController.navigate(Destinations.CONTENT_SEARCH.name)
                }
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.for_you),
                style = TextStyle(
                    textDecoration = if (preference == Preference.FOR_YOU) TextDecoration.Underline else TextDecoration.None,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontFamily = fontFamily,
                    fontWeight = if (preference == Preference.FOR_YOU) FontWeight.ExtraBold else FontWeight.Medium,
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clickable {
                        preference = Preference.FOR_YOU
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.resource__),
                contentDescription = stringResource(id = R.string.search),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(10.dp)
            )
            Text(
                text = stringResource(id = R.string.following),
                style = TextStyle(
                    textDecoration = if (preference == Preference.FOLLOWING) TextDecoration.Underline else TextDecoration.None,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontFamily = fontFamily,
                    fontWeight = if (preference == Preference.FOLLOWING) FontWeight.Bold else MaterialTheme.typography.bodyMedium.fontWeight
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .clickable {
                        preference = Preference.FOLLOWING
                    }
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.outline_search_24),
            contentDescription = stringResource(id = R.string.search),
            tint = MaterialTheme.colorScheme.onSurface,
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
    onClick: () -> Unit,
    liked: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Icon(
        modifier = modifier
            .padding(15.dp)
            .width(35.dp)
            .height(35.dp)
            .clickable {
                onClick()
            },
        painter = painterResource(id = icon),
        contentDescription = stringResource(id = label),
        tint = if (liked) DeepRed else Color.White
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
fun Interactions(modifier: Modifier = Modifier) {
    var liked by remember {
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
            icon = R.drawable.baseline_fullscreen_24,
            label = R.string.fullscreen,
            selected = selected == Interactions.FULLSCREEN,
            onClick = {
                selected = Interactions.FULLSCREEN
            })
        InteractionItem(
            icon = R.drawable.baseline_favorite_24,
            label = R.string.like,
            selected = selected == Interactions.LIKE,
            liked = liked,
            onClick = {
                selected = Interactions.LIKE
                liked = !liked
            })
        InteractionItem(
            icon = R.drawable.baseline_insert_comment_24,
            label = R.string.comment,
            selected = selected == Interactions.COMMENT,
            onClick = { selected = Interactions.COMMENT })
        InteractionItem(
            icon = R.drawable.baseline_bookmark_add_24,
            label = R.string.bookmark,
            selected = selected == Interactions.BOOKMARK,
            onClick = { selected = Interactions.BOOKMARK })
        InteractionItem(
            icon = R.drawable.baseline_ios_share_24,
            label = R.string.share,
            selected = selected == Interactions.SHARE,
            onClick = { selected = Interactions.SHARE })
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
                        .size(75.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.content),
                    contentDescription = contentInfoData.name
                )
                Surface(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .align(Alignment.BottomEnd),
                    color = Color.White
                ) {
                    Icon(
                        modifier = modifier
                            .padding(15.dp)
                            .width(25.dp)
                            .height(25.dp)
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
                        text = "$text ",
                        fontFamily = fontFamily,
                        style = MaterialTheme.typography.bodySmall,
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

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun ContentInfoPreview() {
    ContentInfo(
        contentInfoData = ContentInfoData(
            R.drawable.content,
            R.string.profile,
            "@krayo",
            "Krayo is a platform for artists to share their work with the world. #new #art #artist #ke"
        )
    )
}
