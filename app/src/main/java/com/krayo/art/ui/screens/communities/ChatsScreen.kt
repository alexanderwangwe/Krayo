@file:OptIn(ExperimentalMaterial3Api::class)

package com.krayo.art.ui.screens.communities

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R
import com.krayo.art.constants.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatsScreen(navController: NavController, paddingValues: PaddingValues) {
    val notificationCount = 4
    val chatCount = 10

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier,
        topBar= {
            TopBar(modifier = Modifier, navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            if(notificationCount > 0)
                ChatsNotifications(notificationCount, navController = navController)

            //var withItemLayoutIsShown = remember { derivedStateOf { chatCount > 0 } }
            if (chatCount > 0)
                WithItemLayout()
            else
                NoItemLayout()
        }


    }
}

private enum class TopBarChatState {
    CHAT, COMMUNITIES
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun TopBar(
    modifier: Modifier,
    navController: NavController
) {
    val topBarState = remember { mutableStateOf(TopBarChatState.CHAT) }
    val textColorChat =
        if (topBarState.value == TopBarChatState.CHAT) MaterialTheme.colorScheme.onSurface else Color.Gray
    val textColorCommunities =
        if (topBarState.value == TopBarChatState.COMMUNITIES) MaterialTheme.colorScheme.onSurface else Color.Gray
    val fontStyleChat =
        if (topBarState.value == TopBarChatState.CHAT) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyMedium
    val fontStyleCommunities =
        if (topBarState.value == TopBarChatState.COMMUNITIES) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyMedium
    CenterAlignedTopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                color = textColorChat,
                text = "Chat",
                style = fontStyleChat,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp).clickable {
                    topBarState.value = TopBarChatState.CHAT
                }
            )
            Text(
                text = "Communities",
                color = textColorCommunities,
                style = fontStyleCommunities,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp).clickable {
                    topBarState.value = TopBarChatState.COMMUNITIES
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
                navController.navigate(Destinations.JoinCommunities.name)
            }
        ) {
            Icon(
                Icons.Outlined.Add,
                modifier = Modifier.padding(7.5.dp),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        }
    })
}

@Composable
fun ChatsNotifications(notificationCount: Int, navController: NavController) {
    var buttonIsShown = remember { derivedStateOf { notificationCount > 0 } }

    AnimatedVisibility(visible = buttonIsShown.value) {
        Button(
            onClick = {
                      navController.navigate(Destinations.REQUESTS.name)
            }, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background
            ), modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
        ) {
            Text(
                text = "You have $notificationCount requests",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
@Composable
fun NoItemLayout(modifier: Modifier = Modifier){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.binoculars),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.uh_oh),
            style = MaterialTheme.typography.bodyLarge,
        )

        Text(
            text = stringResource(R.string.you_have_not_started_any_chats_yet),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun Chat(){
    Column(
        modifier = Modifier
            .fillMaxSize().padding(top = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.greycolorimg),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)){
                Text(
                    text = "@genie",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,

                    )
                Text(
                    text = "Thanks for reaching out yeah we have a lot of cool stuff available",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,

                    )
            }
        }
    }
}
@Composable
fun WithItemLayout(modifier: Modifier = Modifier){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(50) {
            Chat()
        }
    }
}
