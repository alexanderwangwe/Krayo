@file:OptIn(ExperimentalMaterial3Api::class)

package com.krayo.art.ui.screens.communities

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.krayo.art.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatsScreen(navController: NavController, paddingValues: PaddingValues) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier,

        topBar= {
            TopAppBar(
                title = {
                    Text(text = "Chat")
                },
                actions = {
                    IconButton(
                        onClick = {
                            // Handle the "Add" button click here
                        }
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                modifier = Modifier.padding(10.dp),
                                tint = MaterialTheme.colorScheme.onSurface,
                                painter = painterResource(id = R.drawable.plus_math),
                                contentDescription = null
                            )
                        }
                    }
                }

            )
        }

    ) {
        ChatsNotifications(it)
        var chatCount = 13
        //var withItemLayoutIsShown = remember { derivedStateOf { chatCount > 0 } }
        if (chatCount > 0) {
            WithItemLayout()

        }else{
            NoItemLayout()
        }


             // Screen Content

    }
}

@Composable
fun ChatsNotifications(paddingValues: PaddingValues) {
    var notificationCount = 14
    var buttonIsShown = remember { derivedStateOf { notificationCount > 0 } }
            Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp).padding(paddingValues)
        ) {
            AnimatedVisibility(visible = buttonIsShown.value) {
                Button(
                    onClick = { /* TODO */ }, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ), modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "You have $notificationCount notifications",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
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
            color = Color.White,
        )

        Text(
            text = stringResource(R.string.you_have_not_started_any_chats_yet),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
        )
    }

}
@Composable
fun WithItemLayout(modifier: Modifier = Modifier){
    Column(
        modifier = Modifier
            .fillMaxSize(),
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
            Column (modifier = Modifier.fillMaxWidth().padding(start = 16.dp)){
                Text(
                    text = "@genie",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,

                )
                Text(
                    text = "Thanks for reaching out yeah we ha..",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,

                )
            }
        }
    }
}
